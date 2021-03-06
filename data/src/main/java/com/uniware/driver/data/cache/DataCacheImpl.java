package com.uniware.driver.data.cache;

import android.content.Context;
import com.uniware.driver.data.repository.datasource.CloudDataStore;
import com.uniware.driver.domain.executor.ThreadExecutor;
import java.io.File;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;

/**
 * {@link DataCache} implementation.
 */
@Singleton public class DataCacheImpl implements DataCache, CloudDataStore {

  private static final String SETTINGS_FILE_NAME = "com.uniware.driver.SETTINGS";
  private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

  private static final String DEFAULT_FILE_NAME = "user_";
  private static final long EXPIRATION_TIME = 60 * 10 * 1000;

  private final Context context;
  private final File cacheDir;
  private final FileManager fileManager;
  private final ThreadExecutor threadExecutor;

  /**
   * Constructor of the class {@link DataCacheImpl}.
   *
   * @param context A
   * @param fileManager {@link FileManager} for saving serialized objects to the file system.
   */
  @Inject public DataCacheImpl(Context context, FileManager fileManager, ThreadExecutor executor) {
    if (context == null || fileManager == null || executor == null) {
      throw new IllegalArgumentException("Invalid null parameter");
    }
    this.context = context.getApplicationContext();
    this.cacheDir = this.context.getCacheDir();
    this.fileManager = fileManager;
    this.threadExecutor = executor;
  }

  @Override public synchronized Observable<Object> get(final int userId) {
/*    return Observable.create(new Observable.OnSubscribe<UserEntity>() {
      @Override
      public void call(Subscriber<? super UserEntity> subscriber) {
        File userEntityFile = DataCacheImpl.this.buildFile(userId);
        String fileContent = DataCacheImpl.this.fileManager.readFileContent(userEntityFile);
        UserEntity userEntity = DataCacheImpl.this.serializer.deserialize(fileContent);

        if (userEntity != null) {
          subscriber.onNext(userEntity);
          subscriber.onCompleted();
        } else {
          subscriber.onError(new OrderNotFoundException());
        }
      }
    });*/
    return null;
  }

  @Override public synchronized void put(Object userEntity) {
    /*if (userEntity != null) {
      File userEntitiyFile = this.buildFile(userEntity.getUserId());
      if (!isCached(userEntity.getUserId())) {
        String jsonString = this.serializer.serialize(userEntity);
        this.executeAsynchronously(new CacheWriter(this.fileManager, userEntitiyFile,
            jsonString));
        setLastCacheUpdateTimeMillis();
      }
    }*/
  }

  @Override public boolean isCached(int userId) {
    File userEntitiyFile = this.buildFile(userId);
    return this.fileManager.exists(userEntitiyFile);
  }

  @Override public boolean isExpired() {
    long currentTime = System.currentTimeMillis();
    long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

    boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

    if (expired) {
      this.evictAll();
    }

    return expired;
  }

  @Override public synchronized void evictAll() {
    this.executeAsynchronously(new CacheEvictor(this.fileManager, this.cacheDir));
  }

  /**
   * Build a file, used to be inserted in the disk cache.
   *
   * @param userId The id user to build the file.
   * @return A valid file.
   */
  private File buildFile(int userId) {
    StringBuilder fileNameBuilder = new StringBuilder();
    fileNameBuilder.append(this.cacheDir.getPath());
    fileNameBuilder.append(File.separator);
    fileNameBuilder.append(DEFAULT_FILE_NAME);
    fileNameBuilder.append(userId);

    return new File(fileNameBuilder.toString());
  }

  /**
   * Set in millis, the last time the cache was accessed.
   */
  private void setLastCacheUpdateTimeMillis() {
    long currentMillis = System.currentTimeMillis();
    this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
        SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
  }

  /**
   * Get in millis, the last time the cache was accessed.
   */
  private long getLastCacheUpdateTimeMillis() {
    return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
        SETTINGS_KEY_LAST_CACHE_UPDATE);
  }

  /**
   * Executes a {@link Runnable} in another Thread.
   *
   * @param runnable {@link Runnable} to execute
   */
  private void executeAsynchronously(Runnable runnable) {
    this.threadExecutor.execute(runnable);
  }

  /**
   * {@link Runnable} class for writing to disk.
   */
  private static class CacheWriter implements Runnable {
    private final FileManager fileManager;
    private final File fileToWrite;
    private final String fileContent;

    CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
      this.fileManager = fileManager;
      this.fileToWrite = fileToWrite;
      this.fileContent = fileContent;
    }

    @Override public void run() {
      this.fileManager.writeToFile(fileToWrite, fileContent);
    }
  }

  /**
   * {@link Runnable} class for evicting all the cached files
   */
  private static class CacheEvictor implements Runnable {
    private final FileManager fileManager;
    private final File cacheDir;

    CacheEvictor(FileManager fileManager, File cacheDir) {
      this.fileManager = fileManager;
      this.cacheDir = cacheDir;
    }

    @Override public void run() {
      this.fileManager.clearDirectory(this.cacheDir);
    }
  }
}

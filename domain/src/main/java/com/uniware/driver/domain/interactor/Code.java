package com.uniware.driver.domain.interactor;

import com.uniware.driver.domain.executor.PostExecutionThread;
import com.uniware.driver.domain.executor.ThreadExecutor;
import com.uniware.driver.domain.repository.Repository;
import rx.Observable;

/**
 * Created by ayue on 2016/9/12.
 */

public class Code extends UseCase {

    private final Repository repository;
    private String phoneNo;

    public Code(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override protected Observable buildUseCaseObservable() {
        return repository.getCode(phoneNo);
    }
    public void setPhoneNo(String phoneNo){
        this.phoneNo=phoneNo;
    }
    public String getPhoneNo(){
        return phoneNo;
    }
}

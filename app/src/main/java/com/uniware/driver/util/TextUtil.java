package com.uniware.driver.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jian on 15/11/11.
 */
public class TextUtil {
  public TextUtil() {
  }

  public static String ToDBC(String var0) {
    char[] var2 = var0.toCharArray();

    for(int var1 = 0; var1 < var2.length; ++var1) {
      if(var2[var1] == 12288) {
        var2[var1] = 32;
      } else if(var2[var1] > '\uff00' && var2[var1] < '｟') {
        var2[var1] -= 'ﻠ';
      }
    }

    return new String(var2);
  }

  public static String ToSBC(String var0) {
    char[] var2 = var0.toCharArray();

    for(int var1 = 0; var1 < var2.length; ++var1) {
      if(var2[var1] == 32) {
        var2[var1] = 12288;
      } else if(var2[var1] < 127) {
        var2[var1] += 'ﻠ';
      }
    }

    return new String(var2);
  }

  public static String cleanPassword(String var0) {
    return isEmpty(var0)?"":var0.replaceAll("[^\\x00-\\xff]*|\\s*", "");
  }

  public static void cleanPasswordEditText(EditText var0, TextWatcher var1) {
    Object var5 = var0.getTag();
    int var2;
    if(var5 != null) {
      try {
        var2 = ((Integer)var5).intValue();
      } catch (ClassCastException var4) {
        return;
      }

      var0.setSelection(var2);
      var0.setTag((Object)null);
    } else {
      String var6 = var0.getText().toString();
      var2 = var0.getSelectionStart();
      int var3 = var6.length();
      var6 = cleanPassword(var6);
      var3 = var2 - (var3 - var6.length());
      var2 = var3;
      if(var3 < 0) {
        var2 = 0;
      }

      var0.setTag(Integer.valueOf(var2));
      var0.setText(var6);
    }
  }

  public static boolean containIllegalChar(String var0) {
    return Pattern.matches(".*[`~!@#$%^&*+=|{}\':;\',\\[\\].<>/?~！@#￥%……&*——+|{}【】‘；：”“’。，、？]+.*",
        var0);
  }

  public static boolean containIllegalCharCompany(String var0) {
    return Pattern.matches(".*[`~!@#$%^&*+=|{}\':;\',\\[\\]./?~！@#￥%……&*+|{}【】‘；：”“’。，、？]+.*", var0);
  }

  public static boolean containIllegalCharUserDes(String var0) {
    return Pattern.matches(".*[`@#$%^&*+=|{}\'\'\\[\\].<>/@#￥%……&*——+|{}【】]+.*", var0);
  }

  public static boolean equals(String var0, String var1) {
    return !isEmpty(var0) && !isEmpty(var1)?var0.trim().equals(var1.trim()):false;
  }

  public static String escapeFileName(String var0) {
    if(var0 == null) {
      return null;
    } else {
      StringBuilder var3 = new StringBuilder();

      for(int var2 = 0; var2 < var0.length(); ++var2) {
        char var1 = var0.charAt(var2);
        if(var1 != 47 && var1 != 92 && var1 != 58 && var1 != 42 && var1 != 63 && var1 != 34 && var1 != 60 && var1 != 62 && var1 != 124) {
          var3.append(var1);
        }
      }

      return var3.toString();
    }
  }

  public static String getIdFromUrl(String var0) {
    return getIdFromUrl(var0, (String) null);
  }

  public static String getIdFromUrl(String var0, String var1) {
    if(!TextUtils.isEmpty(var0) && (TextUtils.isEmpty(var1) || !var0.startsWith(var1))) {
      int var3 = var0.lastIndexOf(".jpg");
      int var2 = var3;
      if(var3 < 0) {
        var2 = var0.length() - 1;
      }

      var3 = var0.lastIndexOf("/");
      int var4 = var0.lastIndexOf("%2F");
      int var5 = var0.lastIndexOf("%252F");
      return var0.substring(Math.max(Math.max(var3 + 1, var4 + 3), var5 + 5), var2);
    } else {
      return var0;
    }
  }

  public static String getIdString(View var0) {
    return String.valueOf(var0.getId());
  }

  public static boolean isCoordinateEmpty(String var0) {
    if(var0 != null) {
      var0 = var0.trim();
      if(var0.length() != 0 && !var0.equals("null") && Double.parseDouble(var0) != 0.0D) {
        return true;
      }
    }

    return false;
  }

  public static boolean isEmpty(String var0) {
    if(var0 != null) {
      var0 = var0.trim();
      if(var0.length() != 0 && !var0.equals("null")) {
        return false;
      }
    }

    return true;
  }

  public static boolean isIllegalCharText(String var0) {
    return Pattern.matches("[`~!@#$%^&*+=|{}\':;\',\\[\\]./?~！@#￥%……&*+|{}【】‘；：”“’。，、？]+", var0);
  }

  public static int length(String var0) {
    return var0 == null?0:var0.length();
  }

  public static long paseLong(String var0) {
    if(isEmpty(var0)) {
      return -9223372036854775808L;
    } else {
      try {
        long var1 = Long.parseLong(var0);
        return var1;
      } catch (ClassCastException var3) {
        return -9223372036854775808L;
      }
    }
  }

  public static void setPasswordFilter(EditText var0) {
    var0.setFilters(new InputFilter[] {
        new InputFilter() {
          @Override
          public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
              int dstart, int dend) {
            return null;
          }
        }
    });
  }

  public static String trim(String var0) {
    return isEmpty(var0)?null:var0.trim();
  }

  public static String trimInner(String var0) {
    String var1;
    if(isEmpty(var0)) {
      var1 = null;
    } else {
      Matcher var2 = Pattern.compile("(\\s+)").matcher(var0);

      while(true) {
        var1 = var0;
        if(!var2.find()) {
          break;
        }

        var0 = var0.replaceFirst(var2.group(1), " ");
      }
    }

    return var1;
  }

  public static String valueOf(float var0) {
    if(Float.isNaN(var0)) {
      return "0";
    } else {
      DecimalFormat var1 = new DecimalFormat();
      var1.setMaximumFractionDigits(2);
      return var1.format((double)var0);
    }
  }
}


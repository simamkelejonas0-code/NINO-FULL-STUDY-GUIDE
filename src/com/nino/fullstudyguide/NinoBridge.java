package com.nino.fullstudyguide;
import android.webkit.JavascriptInterface;
import android.content.Context;
import java.io.*;
public class NinoBridge {
  Context ctx;
  public NinoBridge(Context c){ ctx=c; }
  @JavascriptInterface
  public String askSpark(String question) {
    String key = readKey();
    if (key == null) return "ERROR: mtm_key_txt not found in assets or Download";
    try { return ApiCaller.askSpark(question, key); }
    catch (Exception e) { return "ERROR: " + e.getMessage(); }
  }
  @JavascriptInterface
  public boolean hasKey() { return readKey() != null; }
  String readKey() {
    // 1) assets - always works, no permission needed
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(ctx.getAssets().open("mtm_key_txt")));
      String k = br.readLine(); br.close();
      if (k != null && k.trim().startsWith("LLM_")) return k.trim();
    } catch (Exception e) {}
    // 2) Download folder fallback
    return ApiCaller.readKey();
  }
}

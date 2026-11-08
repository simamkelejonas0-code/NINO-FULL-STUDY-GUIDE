package com.nino.fullstudyguide;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class ApiCaller {
  // Reads key from file on phone, never hard-coded
  public static String readKey() {
    String[] paths = {
      "/sdcard/Download/mtm_key_txt",
      "/storage/emulated/0/Download/mtm_key_txt",
      "/data/data/com.termux/files/home/myAI/mtm_key_txt"
    };
    for (String p : paths) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(p));
        String k = br.readLine();
        br.close();
        if (k != null && k.trim().startsWith("LLM_")) return k.trim();
      } catch (Exception e) {}
    }
    return null;
  }

  public static String askSpark(String question, String key) throws Exception {
    URL url = new URL("https://api.meta.ai/v1/chat/completions");
    HttpURLConnection c = (HttpURLConnection) url.openConnection();
    c.setRequestMethod("POST");
    c.setRequestProperty("Authorization", "Bearer " + key);
    c.setRequestProperty("Content-Type", "application/json");
    c.setDoOutput(true);
    String body = "{\"model\":\"spark-1.3\",\"messages\":[{\"role\":\"user\",\"content\":\"" + question.replace("\"","'") + "\"}]}";
    c.getOutputStream().write(body.getBytes(StandardCharsets.UTF_8));
    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
    StringBuilder out = new StringBuilder(); String line;
    while ((line = br.readLine()) != null) out.append(line);
    br.close();
    return out.toString();
  }
}

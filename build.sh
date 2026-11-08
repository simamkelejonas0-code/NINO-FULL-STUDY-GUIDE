#!/bin/bash
AJ=~/android-sdk/platforms/android-34/android.jar
rm -rf obj gen compiled.zip NINO-FULL-unsigned.apk classes.dex
mkdir -p obj gen
aapt2 compile --dir res -o compiled.zip
aapt2 link -o NINO-FULL-unsigned.apk -I $AJ --manifest AndroidManifest.xml compiled.zip --auto-add-overlay
javac -cp $AJ -d obj -source 8 -target 8 $(find src -name "*.java")
d8 --classpath $AJ --lib $AJ --output . obj/com/nino/fullstudyguide/*.class
zip -r NINO-FULL-unsigned.apk classes.dex assets/
zipalign -f 4 NINO-FULL-unsigned.apk NINO-FULL.apk
apksigner sign --ks ~/myAI/nino2.keystore --ks-pass pass:123456 --key-pass pass:123456 --out NINO-FULL-STUDY-GUIDE-v1.0-CLEAN.apk NINO-FULL.apk

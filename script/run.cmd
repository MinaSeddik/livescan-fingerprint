@ECHO OFF

cd .. && mvn clean install -DskipTests && java -Xms2048m -Xmx2048m -jar target/livescan-fingerprint-0.0.1-SNAPSHOT.jar com.softxpert.livescanfingerprint.LivescanFingerprintApplication

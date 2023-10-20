# ProcNetDev

Read then parse /proc/net/dev in Java

使用Java解析/proc/net/dev

# Usage

```
ProcNetDev d = new ProcNetDev();
d.read("wlan0", "lo"); // Read interface wlan0, lo
String tx = d.get("wlan0").get("Receive_bytes"); // Get Receive bytes on interface wlan0
```


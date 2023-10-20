# ProcNetDev

Read then parse /proc/net/dev in Java

使用Java解析/proc/net/dev

![ProcNetDev_example](https://github.com/NasdaqGodzilla/ProcNetDev/assets/26323326/3b4b8e0e-773c-4433-82a4-b276ceca8061)


# Usage

```
ProcNetDev d = new ProcNetDev();
d.read("wlan0", "lo"); // Read interface wlan0, lo
String tx = d.get("wlan0").get("Receive_bytes"); // Get Receive bytes on interface wlan0
```


# yklockscreen

A tool to lock the screen on macOS when a previously inserted YubiKey is removed.

## Why kscript/Kotlin?

Just because I wanted to try how it would work. I know you could implement the same application in a simple bash or python script.

However I had fun writing it and figure it all out.

## Install as a LaunchAgent in macOS

If you would like to run `yklockscreen` automatically in the background you have to setup a `launchd` configuration.

Edit the file `launchd/yklockscreen.sh` to have the correct `JAVA__HOME` path. This is important if you don't have a system wide Java installation that is Java 8 or newer.

Create a package by running `kscript --package yklockscreen.kts`. This will generate a binary package of the script.

Copy the helper script and executable to `/usr/local/bin`:

```
$ mv yklockscreen /usr/local/bin
$ mv launchd/yklockscreen.sh /usr/local/bin
```

Copy the launchd startscript:

```
$ cp launchd/de.roamingthings.yklockscreen ~/Library/LaunchAgent`

Now load the script using `launchctl load -F ~/Library/LaunchAgents/de.roamingthings.yklockscreen.plist`

To check if the script has been started correctly use `launchctl list | grep yklockscren`

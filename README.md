# spaceteam

## Run
If no RMI registry is running – check that with `ps -a | grep rmiregistry` – then:

```
$ gradle build
$ cd server/out/production/classes
$ rmiregistry 2099 &
```

_Note: it's important to run the process from the root of directory containing compiled classes._

_Another note: if 2099 port is being used (check with `netstat -vanp tcp | grep 2099`) then kill the locking process._

Run mains from:
- `Spaceteam`
- `CaptainDriver`
- `PlayerDriver`

## Stop
Stop all main processes and kill RMI registry with:
```
$ ps -a | grep rmiregistry
$ kill <provide PID>
```
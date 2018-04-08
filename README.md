# spaceteam

## Run
If no RMI registry is running – check that with `ps -a | grep rmiregistry` – then:

```
$ gradle build
$ cd server/out/production/classes
$ rmiregistry &
```

_Note: it's important to run the process from the root of directory containing compiled claseses._

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
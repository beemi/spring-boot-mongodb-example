# Useful commands for Docker


Stop all running containers
```bash
docker stop $(docker ps -a -q)
```

Remove orphaned containers
```bash
docker rm $(docker ps -a -q -f status=exited)
```

List all containers
```bash
docker ps
```

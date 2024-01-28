## Load test with Yandex tank and ghz
### version 3.1.0

### It's a simple example, I highly recommend refactoring this example for using in production aria.

What do we need to install:
1. docker compose 
2. python for yandex tank
3. ghz

Read more about:
 [ya link](https://yandextank.readthedocs.io/en/latest/)

If you cannot use Docker check more information about  settings here: [download and install](https://yandextank.readthedocs.io/en/latest/install.html#installation-from-pypi)

### Yandex tank
folder has:
> ammo-json.txt -> json ammo  
> load.yaml -> main file.  
> tank.sh -> run with docker  
How can you change it or overwrite? First, read documentation here [Yandex load file docs](https://yandextank.readthedocs.io/en/latest/tutorial.html) 

Use as standalone [install with pip3](https://yandextank.readthedocs.io/en/latest/install.html#installation-from-pypi)  

Yaml settings:
[here](https://yandextank.readthedocs.io/en/latest/core_and_modules.html#load-generators)  

Jmeter
[here](https://gist.github.com/sameoldmadness/9abeef4c2125bc760ba2f09ee1150330)  

### `ammo-generator`  
Python script which can create ammo for tank. Used simple generator, like here: [ammo generator example Yandex Tank docs](https://yandextank.readthedocs.io/en/latest/ammo_generators.html)

### ghz
Read more here: [ghz](https://ghz.sh)  

just install it [docs](https://ghz.sh/docs/install)
```shell
brew install ghz
```
config:
```shell
cd .. && cd stock-grpc-service/proto

ghz --insecure \
  --proto ./stocks.proto \
  --call stocks.StocksService.Save \
  -d '{"stocks": { "name":"APPL", "price": "1.3", "description": "apple stocks"} }' \
  -n 2000 \
  -c 20 \
  --connections=10 \
  0.0.0.0:5007
```

## Load test with Yandex tank contest
### version 0.0.3

### It's a simple example, I highly recommend refactoring this example for using in production aria.

`Main goal is an introducing Yandex Tank.
Read more information here:` [ya link](https://yandextank.readthedocs.io/en/latest/)

If you cannot use Docker check more information about  settings here: [download and install](https://yandextank.readthedocs.io/en/latest/install.html#installation-from-pypi)

How to start?  
> run docker compose  
> choose one of services and run (rest/binary)  
> run tank.sh script  
> profit  


---

## Repositories:

### `compose`
> This repo has only one file with mongoDb compose sample.
Mongo UI: http://localhost:8081/


### `document-service`
> REST api sample with GET and POST methods. Just simple demo with MongoDb.  

Tools: 
> Kotlin,  
> Webflux,  
> Reactive Mongo 

### `flutbuffers-document-service`  
REST api sample with POST methods. Just simple demo with MongoDb and Flutbuffers.
Tools: 
> Kotlin,  
> Webflux,  
> Reactive Mongo   
> Flutbuffers  

Read more information about flutbiffers here [flatbuffer docs](https://google.github.io/flatbuffers/).


### `yandex-tank-load-test`   
folder has:
> ammo-json.txt -> json ammo  
> ammo-flutbuffers.txt -> binary ammo  
> load.yaml -> main file.  
> tank.sh -> run with docker  
How can you change it or overwrite? First, read documentation here [Yandex load file docs](https://yandextank.readthedocs.io/en/latest/tutorial.html) 

Use as standalone [install with pip3](https://yandextank.readthedocs.io/en/latest/install.html#installation-from-pypi)  

Yaml settings:
[here](https://yandextank.readthedocs.io/en/latest/core_and_modules.html#load-generators)  

Jmeter
[here](https://gist.github.com/sameoldmadness/9abeef4c2125bc760ba2f09ee1150330)  

### `ammo-generator`  
Python script which can create ammo (json or flatbuffers) for tank. Used simple generator, like here: [ammo generator example Yandex Tank docs](https://yandextank.readthedocs.io/en/latest/ammo_generators.html)


---

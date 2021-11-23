#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import sys
import json

generate_format = "flact"

# http request with entity body template
req_template_w_entity_body = (
        "%s %s HTTP/1.1\r\n"
        "%s\r\n"
        "Content-Length: %d\r\n"
        "\r\n"
        "%s\r\n"
    )

# phantom ammo template
ammo_template = (
        "%d %s\n"
        "%s"
    )

method = "POST"
case = ""
headers = "Host: test.com\r\n" + \
          "User-Agent: tank\r\n" + \
          "Accept: */*\r\n" + \
          "Connection: Close\r\n" 


def make_ammo(method, url, headers, case, body):
    """ makes phantom ammo """
    req = req_template_w_entity_body % (method, url, headers, len(body), body)
    return ammo_template % (len(req), case, req)


def generate_json():
    body = {
        "name": "content",
        "price": 1,
        "amount": 2,
        "description": "description",
        "status": True
    }
    url = "/document/v1/stock/"
    h = headers + "Content-type: application/json"
    s1 = json.dumps(body)
    ammo = make_ammo(method, url, h, case, s1)
    sys.stdout.write(ammo)
    f2 = open("./ammo/ammo-json.txt", "w")
    f2.write(ammo)


def generate_binary():
    f = open("./flatc/new.uu", "r")
    body = f.read()
    url = "/document/v2/stock/"
    h = headers + "Content-type: application/x-flatbuffers"
    ammo = make_ammo(method, url, h, case, body)
    sys.stdout.write(ammo)
    f2 = open("./ammo/ammo-fb-1.txt", "w")
    f2.write(ammo)


if __name__ == "__main__":
    if generate_format == "json":
        generate_json()
    else:
        generate_binary()

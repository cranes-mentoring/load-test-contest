#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import sys
import json

# Change it!
generate_json = True


def make_ammo(method, url, headers, case, body):
    """ makes phantom ammo """
    # http request w/o entity body template
    req_template = (
        "%s %s HTTP/1.1\r\n"
        "%s\r\n"
        "\r\n"
    )

    # http request with entity body template
    req_template_w_entity_body = (
        "%s %s HTTP/1.1\r\n"
        "%s\r\n"
        "Content-Length: %d\r\n"
        "\r\n"
        "%s\r\n"
    )

    if not body:
        req = req_template % (method, url, headers)
    else:
        req = req_template_w_entity_body % (method, url, headers, len(body), body)

    # phantom ammo template
    ammo_template = (
        "%d %s\n"
        "%s"
    )

    return ammo_template % (len(req), case, req)


def generate_json():
    body = {
        "name": "content",
        "price": 1,
        "amount": 2,
        "description": "description",
        "status": True
    }
    method = "POST"
    url = "/document/v1/stock/"
    case = ""
    headers = "Host: test.com\r\n" + \
              "User-Agent: tank\r\n" + \
              "Accept: */*\r\n" + \
              "Connection: Close\r\n" + \
              "Content-type: application/json"

    s1 = json.dumps(body)
    ammo = make_ammo(method, url, headers, case, s1)
    sys.stdout.write(ammo)

    f2 = open("./ammo/ammo-json.txt", "w")
    f2.write(ammo)


def generate_binary():
    f = open("./flatc/new.uu", "r")
    body = f.read()
    method = "POST"
    url = "/document/v2/stock/"
    case = ""

    headers = "Host: test.com\r\n" + \
              "User-Agent: tank\r\n" + \
              "Accept: */*\r\n" + \
              "Connection: Close\r\n" + \
              "Content-type: application/x-flatbuffers"

    ammo = make_ammo(method, url, headers, case, body)
    sys.stdout.write(ammo)

    f2 = open("./ammo/ammo-fb-1.txt", "w")
    f2.write(ammo)


if __name__ == "__main__":
    if generate_json:
        generate_json()
    else:
        generate_binary()

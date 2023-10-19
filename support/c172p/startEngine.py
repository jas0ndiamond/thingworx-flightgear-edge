#!/usr/bin/python3 -W ignore

# -W ignore above for deprecation warning about telnetlib in python3
import telnetlib

host = "127.0.0.1"
port = 5001

print("Connecting...")
tn = telnetlib.Telnet(host, port)

print("Getting prompt...")
tn.write(b"\r\n")
tn.read_until(b"/>")

print("Starting engine...")
tn.write(b"nasal\r\n")
tn.write(b"c172p.autostart();\r\n")
tn.write(b"##EOF##\r\n")

print("Completed")

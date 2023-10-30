# TotalBypass
Permissions-based VPN-blocker bypass for MinecraftAFK Web version

## Supported permissions plugins

- LuckPerms

## Supported VPN-blocker plugins

- KauriVPN (through permission antivpn.bypass)


## Principle of operation

- Send a Login Plugin Message on channel `minecraftafk:token`.
- If client doesn't respond with a token, treat the user normally (i.e. no further processing by this plugin)
- If client responds, verify token with https://minecraftafk.com/api/verify - body params `token` and `username`
- If token is valid (signified by a 200 response with body `VERIFIED`), add `antivpn.bypass` permission to user
- If token is invalid, disconnect user
- Once connection is complete, remove `antivpn.bypass` permission from user

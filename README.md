# OpenEly

A open source alterative to the Ely.by skin system plugin.

## Why?

Ely.by's skin system plugin is closed source and is limited to only BungeeCord and Bukkit/Spigot. Even after trying to email them twice, they not only ignored said emails, but updated their plugin to be obfuscated, making it hard to vet the code. I made this because I needed something that worked on modern proxies and server software, while also knowing the code is safe.

## What server software does this work on?

- [x] Velocity
- [ ] BungeeCord / Waterfall
- [ ] Paper / Spigot / Bukkit
- [x] SpongeAPI4 (1.8 - 1.12.2)
- [x] SpongeAPI8 (1.16.5 - 1.20+)
- [ ] Forge?
- [ ] Fabric?

Unchecked server software means there is no release for it yet, but it is planned.

## Other questions

### Does this work with offline mode?

It should just work exactly the same as the Ely.by plugin and SkinsRestorer. All it does is take the username and grab the skin from the Ely.by API.

### Can this replace Mojang skins?

Yes, but by default it doesn't. If you want to replace mojang skins by default, you can set `replace-mojang-skins` to `true` in the config. Users can still use `/openely enable` to enable their ely.by skin.

### Can I set my skin to someone else's skin?

No, and I don't plan on adding support for it. If you want to do that, use SkinsRestorer.

### Does this support custom capes?

No, unless Ely.by adds support for capes, there is no point in adding support for them.

### Can you add support for changing skins without an Ely.by account?

No, as there is no clean way to grab a skin from Ely.by without it being attached to an account.

## How to install

1. Download the latest release for the server software you are using.
2. Put the plugin in your plugins folder.
3. Start your server.
4. Profit.

## Can I contribute?

Feel free to make a pull request to help fix my horrible code or add support for other server software. I hate Java with a passion so helping me with this would be greatly appreciated.

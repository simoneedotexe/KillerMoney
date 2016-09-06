# KillerMoney v3 (Bukkit plugin)

When a player kill an entity, this plugins let you charge/give money or even give any item or run a command.

### Abilities of the plugin
- Giving or chargeing a player by either a fix amount of money, or random between a max and minimum amount when they kill an entity
- Charging the victim of a PVP
- Fully customisable loot dropping when an entity die
- Running a command of your choice when an entity die
- Banning the ability to farm entities for money,if they were spawned by either eggs or spawners 
- Multiworld support for entities
- Full modularity, the only thing you need to add to the config file is what you need!
- Customisable messages, different languages supported
- Permission support by each entity for a perfect rank system
- Money multiplier permission - killermoney.multiplier.1...10 
- In both Minecraft and Cauldron server installed modifications, entities are supported
- Public API 

### Commands
- /killermoney help - Help
- /killermoney info - Informations
- /killermoney reload - Reloading config files 

### Permissions
- killermoney.cmd.reload - /killermoney reload permission
- killermoney.multiplier.1...10 - Money multiplier permission ( minimum 1, maximum 10 )
- Anything else that you gave for entities! 

### Installing guide
- Step 0: Install VAULT and Economy plugins ( for example Essentials Eco, BOSEconomy, iConomy, etc. ) If you have either KillerMoney 1.x or 2.x installed, delete it with all files and configurations
- Step 1: Download the latest version of KillerMoney and copy it into your plugins folder.
- Step 2: Restart your server for the plugin files to generate and for the plugin to load.
- Step 3: Customise mobs.yml file to your like and set your language in the locale.yml file.
- Step 4: Use it and send feedback for future developments 

### Developers

Do not forget to add ```softdepend: [KillerMoney]``` or ```depend: [KillerMoney]``` to your plugin.yml

```java
public static KillerMoneyAPI kmApi = null;

@Override
public void onEnable() {
    Plugin km = Bukkit.getPluginManager().getPlugin("KillerMoney");

    if ((km != null) && (km.isEnabled())) {

        kmApi = new KillerMoneyAPI();

    }
}
```

JavaDocs: http://diecode.net/plugins/KillerMoney/JavaDocs/
## Entities config

```yaml
PLAYER:
    '*':
        Money:
            Enabled: true
            Chance: 100%
            Value: 100 ? 200
            Division-method: 'LAST_HIT'
            Permission: 'km.custom.permission'
            Limit: 2000
        Custom-commands:
            Enabled: true
            Run-method: 'ALL'
            Commands:
                '1':
                    Command: 'say {player} killed an entity'
                    Chance: 12.5%
                    Permission: 'km.custom.permission'
                    Limit: 10
                '2':
                    Command: 'give {player} DIAMOND 5'
                    Chance: 0.5%
                    Permission: 'km.custom.permission'
                    Limit: 15
        Custom-item-drop:
            Enabled: true
            Keep-default-items: false
            Run-method: 'ALL'
            Items:
                '1':
                    ItemStack:
                        ==: org.bukkit.inventory.ItemStack
                        type: GRASS
                    Random-amount: 1 ? 20
                    Chance: 100%
                    Permission: 'km.custom.permission'
                    Limit: 200
                '2':
                    ItemStack:
                        ==: org.bukkit.inventory.ItemStack
                        type: DIAMOND_SWORD
                    Chance: 80%
                    Permission: 'km.custom.permission'
                    Limit: 200
        Cash-transfer:
            Enabled: true
            Percent: 25%
            Max-amount: 5000
            Chance: 100%
            Division-method: 'LAST_HIT'
            Permission: 'km.custom.permission'
            Limit: 10000
```

## Configuration manual

```yaml
PLAYER:
```

This is the entity name. The list depend from server version. [Check latest spigot list here](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/EntityType.html)

```yaml
'*':
```

The second row the world name. The '*' mean that the rules valid on all worlds. You can change it to world name or you can add more worlds with commas.

Example: **'world, world_nether'**

> You can add more rewards accordings worlds:

```yaml
PLAYER:
    'world':
        Money:
            Enabled: true
            Chance: 75%
            Value: 100
    'world_nether, world_the_end':
        Money:
            Enabled: true
            Chance: 100%
            Value: 200
    'mine':
        Money:
            Enabled: true
            Chance: 5%
            Value: 1500
```

```yaml
Money:
    Enabled: true
    Chance: 100%
    Value: 100 ? 200
    Division-method: 'LAST_HIT'
    Permission: 'km.custom.permission'
    Limit: 2000
```

This is the money reward section.

+ **Enabled:** You can enable or disable it. Accepted values: true or false _<optional, default value is true>_
+ **Chance:** Reward chance. Min: 1%, max: 100% _<optional, default value is 100%>_
+ **Value:** The money amount. You can set random money between two numbers, or you can set up fix amount.

+ Example for random: **Value: 100 ? 200**
+ Example for fix: **Value: 100**

You can set negative numbers too: **Value: -50 ? 50**

+ **Division-method:** Acceptable values: "LAST_HIT" or "SHARED". If you choose SHARED, then the reward will divide between entity damagers. LAST_HIT will reward only the killer (last damager) with full amount of reward. _<optional, default value is LAST_HIT>_
+ **Permission:** You can set custom permission. If player hasn't this perm, then player will not get reward. _<optional, there isn't default value>_
+ **Limit:** If players reached limit with money earning, then players will not get reward while server restart or limit reset. You can set limit reset time in **config.yml** and you can reset it manually with **/km limit reset** command. _<optional, there isn't default value>_

```yaml
Custom-commands:
    Enabled: true
    Run-method: 'ALL'
    Commands:
        '1':
            Command: 'say {player} killed an entity'
            Chance: 12.5%
            Permission: 'km.custom.permission'
            Limit: 10
        '2':
            Command: 'give {player} DIAMOND 5'
            Chance: 0.5%
            Permission: 'km.custom.permission'
            Limit: 15
```

You can execute commands when players kill entities.

+ **Enabled:** You can enable or disable it. Accepted values: true or false _<optional, default value is true>_
+ **Run-method:** Acceptable values: "ALL" or "RANDOM". If you choose "ALL", then all command will execute. If random, then only one. _<optional, default value is ALL>_
+ **"Number:"** If you add more commands, you must increment the number. Same number is not allowed.
+ **Command:** That command which you want execute. The {player} will be replaced with killer's name.
+ **Chance:** Command chance. Min: 1%, max: 100% _<optional, default value is 100%>_
+ **Permission:** You can set custom permission. If player hasn't this perm, then the command will not be execute. _<optional, there isn't default value>_
+ **Limit:** If players reached limit with money earning, then players will not get reward while server restart or limit reset. You can set limit reset time in **config.yml** and you can reset it manually with **/km limit reset** command. _<optional, there isn't default value>_

```yaml
Custom-item-drop:
    Enabled: true
    Keep-default-items: false
    Run-method: 'ALL'
    Items:
        '1':
            ItemStack:
                ==: org.bukkit.inventory.ItemStack
                type: GRASS
            Random-amount: 1 ? 20
            Chance: 100%
            Permission: 'km.custom.permission'
            Limit: 200
        '2':
            ItemStack:
                ==: org.bukkit.inventory.ItemStack
                type: DIAMOND_SWORD
            Random-amount: 1 ? 20
            Chance: 80%
            Permission: 'km.custom.permission'
            Limit: 200
```

Set custom item drop

+ **Enabled:** You can enable or disable it. Accepted values: true or false _<optional, default value is true>_
+ **Keep-default-items:** If you set it true, then will be drop the default items (leather, food or other) too. _<optional, default value is true>_
+ **Run-method:** Acceptable values: "ALL" or "RANDOM". If you choose "ALL", then all command will execute. If random, then only one. _<optional, default value is ALL>_
+ **"Number:"** If you add more commands, you must increment the number. Same number is not allowed.

> DO NOT MODIFY THIS
```yaml
ItemStack:
    ==: org.bukkit.inventory.ItemStack
```

+ **type:** MATERIAL name, [Acceptable values](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html) (depend from server version)

> ItemStack examples

+ **Leaping potion**
```yaml
ItemStack:
    ==: org.bukkit.inventory.ItemStack
    type: POTION
    damage: 8235
```

+ **Sheep spawner egg**
```yaml
ItemStack:
    ==: org.bukkit.inventory.ItemStack
    type: MONSTER_EGG
    damage: 91
```

+ **Gold ingot with amount settings and item meta**
```yaml
ItemStack:
    ==: org.bukkit.inventory.ItemStack
    type: GOLD_INGOT
        amount: 32
        meta:
          ==: ItemMeta
          meta-type: UNSPECIFIC
          display-name: "§rAmazing gold ingot"
          lore:
          - "Nicest"
```

+ **Iron sword with enchants**
```yaml
ItemStack:
    ==: org.bukkit.inventory.ItemStack
    type: IRON_SWORD
        damage: 1
        amount: 1
        meta:
            ==: ItemMeta
            meta-type: UNSPECIFIC
            enchants:
                SHARPNESS: 1
```

+ **Random-amount:** Set random amount for ItemStack. If you don't want random amount, just remove it. _<optional, there isn't default value>_
+ **Chance:** Command chance. Min: 1%, max: 100% _<optional, default value is 100%>_
+ **Permission:** You can set custom permission. If player hasn't this perm, then the command will not be execute. _<optional, there isn't default value>_
+ **Limit:** If players reached limit with money earning, then players will not get reward while server restart or limit reset. You can set limit reset time in **config.yml** and you can reset it manually with **/km limit reset** command. _<optional, there isn't default value>_

> You can only use this in PLAYER entity section.
```yaml
Cash-transfer:
    Enabled: true
    Percent: 25%
    Max-amount: 5000
    Chance: 100%
    Division-method: 'LAST_HIT'
    Permission: 'km.custom.permission'
    Limit: 10000
```

Transfer money between killer (earn) and victim (lose)

+ **Enabled:** You can enable or disable it. Accepted values: true or false _<optional, default value is true>_
+ **Percent:** Percent of victim's money which killer will earn. Min: 1%, max: 100%
+ **Max-amount:** Maximum earning limit in one kill. _<optional, there isn't default value>_
+ **Chance:** Transfer chance. Min: 1%, max: 100% _<optional, default value is 100%>_
+ **Division-method:** Acceptable values: "LAST_HIT" or "SHARED". If you choose SHARED, then the reward will divide between entity damagers. LAST_HIT will reward only the killer (last damager) with full amount of reward. _<optional, default value is LAST_HIT>_
+ **Limit:** If players reached limit with money earning, then players will not get reward while server restart or limit reset. You can set limit reset time in **config.yml** and you can reset it manually with **/km limit reset** command. _<optional, there isn't default value>_
# MobControl
This plugin allows you to control any mob you want!
Right click on it, or select an option in your hotbar.
NOTE: This plugin is based off of Dream's videos, but all of the
code in this plugin was written by me.

### Commands
All of the commands are accessed by using `/mobcontrol`.  
`\mobcontrol set [player]` allows you to set the person who will be controlling mobs. They will have the options added
into their hotbar, mobs will stop attacking them, and they will be invisible.  
`\mobcontrol remove [player]` undoes everything the previous command did. They will be visible again, their inventories
will be cleaned, and mobs will attack them again.

## Hotbar controls
### Overworld
Without any disguises on, you will see this hotbar:  
![hotbar1](https://i.ibb.co/Qfkj70r/hotbar.png "hotbar 1")  
You can choose an option by right-clicking the item.  
Note: if you have the compass in your hotbar, you can right click a mob to control it directly, instead of controlling the closest one.

Once you control a mob, you will have different settings. They vary from mob to mob, but most of them have the following layout:  
![hotbar2](https://i.ibb.co/9pHtK4s/default-hotbar.png "default hotbar")  
If you are controlling a hostile mob, you can punch people by selecting the empty slot. Some other mobs, though,
have a special action on the first slot. Here is a list of them:

#### Creeper
![hotbar3](https://i.ibb.co/4P458wN/creeper-hotbar.png "creeper hotbar")  
If you right click while holding the TNT, the creeper will be primed and you will explode after a small delay. You will stop controlling the creeper, and it will die.

#### Drowned
![hotbar4_trident](https://i.ibb.co/6JkgVBx/drowned.png "drowned hotbar")  
As most Drowned don't spawn with a trident, you will have a *20%* chance of having one. If you don't, you can still attack with your hands. The tridents have loyalty  
so you will never lose it. Note: Drowned are undead mobs, so you will **burn** if exposed to sunlight. More details below.

#### Enderman
![hotbar5](https://i.ibb.co/PNYm3S9/enderman-hotbar.png "enderman hotbar")  
To teleport, look at the place you want to teleport to, select the ender pearl, and right click. If it is too far away, you will stay in place.  
If you are in danger and want to escape, you can right click on the chorus fruit (flee) and you will be teleported randomly.  
If you want to attack, you can attack with the stick.

#### Skeleton
![hotbar6](https://i.ibb.co/wYsXjgs/skeleton-hotbar.png "skeleton hotbar")  
Skeletons are self explanatory. You can shoot with the bow.  
NOTE: Skeletons are also undead mobs, so they'll also burn in the sunlight.

#### Witch
![hotbar7](https://i.ibb.co/CBZqzMH/witch-hotbar.png "witch hotbar")  
As a witch, you can throw a random negative potion, or regenerate health.
Potions are chosen using a bag randomizer, which means you are guaranteed to get one of each potion (slowness, harming, poison and weakness) every
four potions. Each one has a 5% chance of being upgraded and a 5% chance of being extended (mutually exclusive).
If you are running low on health, you can drink an Instant Healing potion.

### Nether
The default hotbar on the Nether is this:  
![hotbar8](https://i.ibb.co/tBNLbHJ/nether-hotbar.png "nether hotbar")  
Basically the same, but with different mobs. All of them, except skeletons and endermen, are lava-proof and fire-proof.

#### Blaze
![hotbar9](https://i.ibb.co/s17vLC2/blaze-hotbar.png "blaze hotbar")  
As Blazes can fly, you will too if you control them. You can't stop flying though, unless you stop controlling it.  
If you select the first slot on the hotbar and right-click, you will emit a breathing sound effect, and throw three  
fireballs in the direction you are looking at.

#### Ghast
![hotbar10](https://i.ibb.co/GT8cN7K/ghast-hotbar.png "ghast hotbar")  
Similarly, you will fly when controlling Ghasts, and you will throw a fire charge if you right click on the first slot.  
Be careful, though - you can shoot yourself if you're close to a wall or ceiling!

#### Wither Skeleton
![hotbar11](https://i.ibb.co/zZcD8Vq/witherskel-hotbar.png "wither skeleton hotbar")  
Any mob you attack with the stone sword (slot 1) will have the Withering effect for 10 seconds.

### The End
You can only control the endermen in this dimension. Support for controlling the ender dragon will come in the future.

## Gameplay changes
- As an enderman, you will forcefully teleport randomly if you come in contact with water. You will lose one heart of health also.
- As a skeleton, zombie, or drowned, you will catch on fire if you are exposed to sunlight.
- Creepers, zombies and iron golems have slower movement speeds.
- Drowned, fish, squids and other water mobs will give you water breathing if you control them.
- You will have the Dolphin's Grace effect if you control a dolphin.
- Mobs native to the nether will make you lava-proof and fire-proof.

## Cooldowns
Some items have a cooldown timer to balance the gameplay. Some examples are:
- Skeleton bows
- Making creepers explode
- Attacking as an enderman
- Attacking as a wither skeleton
- Drinking a health potion
- Throwing a fireball/fire charge
- Teleportation and fleeing
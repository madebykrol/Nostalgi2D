# Nostalgi2D
Nostalgi2D is a open source multiplatform 2D game engine built ontop of libgdx.
It relies on the TMX map format, so Tiled is recommended for designing your levels for games built with Nostalgi2D
Alot of the design choices for the engine has been inspired by Unreal Engine so if you have experimented with that engine before you might recoginize some of the concepts

## Your first project.
It is recommended that you build your projects using Android studios as of this writing and it has only been tested on a PC.
When you start your first project you will find a couple of classes inside your core/src/com/nostalgi/game that is showing you some examples on how to wire things up

### Assets.
All of the assets reside in the android module. (android/assets) this is to make managing your assets as convinient as possible.
What you have to do to build and run a desktop project you need to link your resources by setting the working directory to working android/assets by editing the run configuration for the DesktopLauncher.

#### Maps
Maps are designed using Tiled, how you structure your maps folder is up to you.

#### Note on bundled assets.
Currently there are a bunch of bundled free assets, these will be removed and changed into something else once the engine is in beta / release.
So please do not rely on these assets without giving credits to the designers in your live projects

### Controllers
In a Nostalgi2D Game, a Controller is conceptually the player, or rather what handles the players input. The engine is associating the controller with a playersession(?) and a playerstate.


<i>(?)Playersession - The engine keeps track of a player in a multiplayer context with a playersession that is tied to a state and a controller. In a singleplayer context it just tracks a single session. locally.</i>
### Actors
Actors are the objects that brings life to your game. It could be characters, doors, lamps, spawners, ponds, or anything else that is going to be acting with or to players or other events in the world.

### GameMode
The game mode is what governs the rules of your game. Each map is associated with a game mode.

### GameState
The current state of the game. This state is associated with the current played map, for example it can track of pickups that has been picked up and that will be reset after a while

## Roadmap
<ul>
<li><s>Character Possession</s></li>
<li><s>Trigger Volumes</s></li>
<li><s>Physics movement</s></li>
<li><s>Raycasting</s></li>
<li><s>AABB Querying</s></li>
<li><s>Multiple shape / fixtures per physics body on actors</s></li>
<li><s>Floors and layered rendering (4 floors default)</s></li>
<li><s>Navigation system and Pathfinding</s>(Alpha - unstable)</li>
<li><s>Spatial sound sources</s>(Alpha - Unstable)</li>
<li><s>Lighting effects</s>(Bundling Boxlight2D)</li>
<li><s>Timers</s></li>
<li><s>Timelines</s></li>
<li><s>Level travelling</s></li>
<li>Behavior trees</li>
<li>Animated tiles</li>
<li>Network / Multiplayer capabilities</li>
<li>Custom map editor instead of Tiled</li>
</ul>

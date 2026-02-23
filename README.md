# 🧱 MiniCraft

A sandbox-survival game built in Java with a modular object oriented architecture and a Behavior-Driven Development (BDD) workflow.

Inspired by classic crafting games, MiniCraft challenges players to explore, gather resources, craft tools, and survive in a procedurally generated world.

---

## 🚀 Features

* 🌍 **Procedural World Generation** — Biomes, tile diversity, and block placement driven by layered noise algorithms
* 🪓 **Resource Gathering & Crafting** — Break blocks, collect resources, and craft tools and potions via inventory interface
* ⏳ **Game Loop & Day/Night Cycle** — Dynamic environment evolution and enemy behavior
* 🧠 **Pathfinding Mobs** — Passive NPCs and hostile enemies with A\* pathfinding
* 🎛️ **Custom GUI** — Smooth player-centric movement, clean HUD, responsive menus
* 💾 **Save/Load Support** — Persist your progress across sessions
* 🔍 **Tested with BDD** — All core mechanics validated through Cucumber-based feature tests

---

## 🛠 Architecture & Development

* **Modular OOP**
  Uses an MVC architecture with clean separation between `Model`, `View`, and `Controller` layers. Classes like `Player`, `Enemy`, `Tile`, `Tool`, and `Inventory` reflect single-responsibility design.

* **Design Patterns**

  * **Singleton**: Global state managers like `Player`, `World`, and `SoundManager`
  * **Composite**: GUI components with nested rendering
  * **Factory**: Tool and block creation logic

* **S.O.L.I.D. Principles**
  Enforced during feature implementation and group refactoring sessions

* **Behavior-Driven Development (BDD)**
  User stories written in Gherkin and validated via Cucumber before feature implementation

---

## 💻 Installation & Launch

1. Download the latest [release](https://github.com/Lex-VC/MiniCraft/releases).
   
2. Run the .jar file.
   
3. Enjoy!


---


###🚦 Getting Started

* **New Game** — Starts a fresh session.
* **Load Game** — Loads the latest save.
* **Quit** — Exits the game.

---

### 🕹 Controls

| Action            | Key(s)                   |
| ----------------- | ------------------------ |
| Movement          | W, A, S, D or Arrow Keys |
| Interact / Action | SPACE                    |
| Switch Inventory  | Q                        |
| Cycle Items       | E                        |
| Pause / Options   | ESC or O                 |

---

### 🌍 Gameplay Mechanics

* Explore a procedurally generated world filled with diverse biomes and blocks
* Break chests to obtain random tool upgrades or potions
* Use specific tools for specific blocks (e.g. axe for wood, pickaxe for stone)
* Monitor health via HUD; manage tools and potions via inventory
* Encounter friendly NPCs and hostile mobs (zombies, skeletons)
* Combat enemies using swords (the only weapon that deals damage)

---

### 📺 Interface and HUD

* Health and inventory are shown in the lower-left corner
* Day/night cycle impacts visibility and enemy spawning

---

### ⚙ Options & Settings

Access the Options Menu during gameplay by pressing `ESC` or `O`:

* **Back to Game** — Resume playing
* **Save Game** — Store current game state
* **Quit Game** — Exit the application

---

### 💡Quick Tips

* Break chests early for useful random drops
* Organize your inventory for fast switching
* Pause the game to access saving and settings

---

## 🧪 Testing

* **Unit Tests**: Written using JUnit
* **BDD Tests**: Written in Gherkin, executed via Cucumber
* **CI**: GitLab pipeline runs on each commit to ensure all tests pass

---

## 👥 Team

* **Lex Van Cauter** — World generation, Game Design, Project Management
* **Mateo Iturmendi** — GUI, UX, visual and audio assets
* **Mairo Trump** — Controllers, world positioning, day/night cycle
* **Yasmine Kennou Filali** — Inventory, tools, health, mobs
* **Stefan Pizlo** — Testing lead, save/load system, BDD integration

---

## 🔗 Resources

* 🎥 Demo: [Watch on YouTube](https://youtu.be/DSZsZ1BliLY)

---

Thanks for playing 🪓

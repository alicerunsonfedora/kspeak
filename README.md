# kspeak

A Kotlin DSL for writing visual novel-like and cutscene scripts for games

> :warning: This library is experimental and not complete. Use at your own risk.

## Getting started

Add the following information to your `build.gradle.kts` file:

```kotlin
// MERGE THIS WITH YOUR REPOSITORIES ENTRY
repositories {
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/alicerunsonfedora/kspeak")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
        }
    }
}

// MERGE THIS WITH YOUR DEPENDENCIES ENTRY
dependencies {
    implementation("kspeak:lib:1.0-SNAPSHOT")
    implementation(kotlin("script-runtime"))
}
```

Create a personal access token with access to `packages`. In your `gradle.properties` file, add the following:

```
gpr.user=yourGitHubUserName
gpr.key=yourGitHubToken
```

You should now be able to reload Gradle and install the library.

## Usage

It is recommended to use `kspeak` in a Kotlin script to auto-generate files when running them.

An example script looks like:

```kotlin
import kspeak.*

buildScene {
    version(1)
    outputToFile("helloWorld.json")
    
    parts {
        dialogue {
            narrate("Hello, world!")
        }
    }
}

```

### Creating a scene

The `buildScene` method allows you to dynamically create a scene that can be exported to JSON.

First, define a version and the output path. The version should remain `1` unless using a later schema version:

```kotlin
import kspeak.*

buildScene {
    version(1)
    outputToFile("path/to/your/json/file/here.json")
}
```

If you are using `buildScene` in your code and want to use the JSON string directly, you can omit `outputToFile`.

### Defining parts

The majority of the scripting occurs in `parts`, which lets you define the list of parts in the script.

There are two kinds of parts currently:

- `dialogue`, which lets you write a block of dialogue for characters and narrators.
- `branch`, which lets you write decision branches with options that contain dialogue.

### Characters

Characters are defined with the `Character` class and are used to make up dialogue and decision branches. These should
be instantiated _before_ dialogue and branch parts:

```
parts {
    val player = Character("Player")
}
```

The `Character` class takes in a single parameter, `name`. This name can be referenced by calling `toString()` on the
class or in string interpolation:

```kotlin
val player = Character("Player")
println("The character's name is $player.")
```

### Dialogue

To write a dialogue block, call `dialogue` in the `parts` method:

```kotlin
parts {
    dialogue {
        
    }
}

```

There are three different ways of writing dialogue:

- `narrate`, which takes in a single string for narration
- `line`, which takes in the result of `Character.speak`
- `monologue`, which takes in commands of `say`

To write a line for narration, call `narrate`, which automatically creates a narrator character:

```kotlin
dialogue {
    narrate("I gently open the door.")
}
```

To have a character speak a line, call on `Character.speak` in conjunction with `line`:

```kotlin
val player = Character("Player")
dialogue {
    line(player.speak("Hello, world!"))
}
```

To specify that a certain image name be shown, add the `imageName` parameter:

```kotlin
val player = Character("Player")
dialogue {
    line(player.speak("How are you all?", imageName = "wave"))
}
```

To write a monologue or group lines together, call `monologue`. Lines can be instantiated by using the `say` operator:

```kotlin
val player = Character("Player")
val katorin = Character("Katorin")

dialogue {
    monologue {
        this say player.speak("Hey, $katorin...")
        this say player.speak("Did the package compile?")
    }
}
```

### Decision Branches

Create decision branches with the `branch` method. Each option will contain a prompt and dialogue (see: Dialogue):

```kotlin
branch {
    option("Go to the store.") {
        narrate("I decide to go to the store.")
    }
    option("Go to the cafe.") {
        narrate("Maybe I should get coffee first...")
    }
}
```

To have a player cycle through all options before reaching the next block, add the parameter `waitForAll`:

```kotlin
branch(waitForAll = true) {
    option("Rock") { }
    option("Scissors") { }
    option("Paper") { }
}
```

#### Options

Define options with the `option` method, which takes in a `name` that appears on screen and a block of dialogue:

```kotlin
option("Call Luma.") {
    narrate("Calling her wouldn't be a bad idea.")
}
```

### JSON Format

When serialized and exported, the JSON file will contain similarly-constructed data of the scene script:

As an example:

```json
{
  "version": "1",
  "parts": [
    {
      "kind": "DIALOGUE",
      "dialogue": [
        {"narrator":  "Hello, world!"},
        {
          "Player":  "Hey, all.",
          "image": "feetUpOnTable"
        }
      ]
    },
    {
      "kind": "BRANCH",
      "branch": [
        {
          "name": "Wave.",
          "dialogue": [
            {"narrator":  "I wave to everyone."}
          ]
        }
      ],
      "options": {
        "waitForAll": false
      }
    }
  ]
}
```

The format should be readable and allow developers to write dialogue UI or other systems that can render the appropriate
data.
# kspeak

A Kotlin DSL for writing visual novel-like and cutscene scripts. Ideal for Godot Kotlin/JVM.

> :warning: This library is still under construction and needs proper documentation.

## Example
You can use a Kotlin script to generate scripts using the DSL:

```kotlin

import kspeak.*

buildScene {
    version(1)
    outputToFile("exampleChapter.json")
    
    parts {
        val player = Character("You")
        val john = Character("John")
        
        dialogue {
            monologue {
                this say player.speak("I didn't think I'd get this far.")
                this say player.speak("Just a few more lines of code, and I'll be done!")
            }
        }
        
        branch {
            option("Compile now") {
                narrate(player.speak("I decide to compile the project."))
            }
            
            option("Run tests") {
                narrate(player.speak("I decide to run the tests."))
            }
        }
        
        dialogue {
            narrate(player.speak("And it worked!"))
        }
    }
}

```
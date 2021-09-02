import kspeak.*

buildScene {
    version(1)
    outputToFile("output.json")

    parts {
        val player = Character("Player")
        val john = Character("John")

        dialogue {
            narrate("I haven't felt this way in a long time.")
            narrate("I feel the blood rushing to my head, watching this compile...")
            narrate("Success!")

            line(john.speak("Hey, $player, what are you up to?"))
            narrate("I jolt up, startled.")

            monologue {
                this say player.speak("Ahh! I didn't see you come in.")
                this say john.speak("The door was open.", imageName = "gesture1")
                this say player.speak("Oh.")
                this say player.speak("I, uh, was compiling a package...")
            }

            line(john.speak("Ah. You want to come to the theater with me?"))
        }

        branch {
            option("Sure.") {
                line(john.speak("Great. I'll see you in an hour."))
            }
            option("Maybe next time.") {
                line(john.speak("Sure, I'll let you know of any future shows."))
            }
        }

        dialogue {
            narrate("John leaves the room.")
        }


    }
}
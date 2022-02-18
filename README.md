# Summary

For this project, I aimed to demonstrate my expertise with the following topics while also delivering on all of the challenge requirements.

- Familiarity with the latest Android tech stacks and libraries.
  
  - Jetpack Compose
    
  - Dagger Hilt
    
  - Navigation, UI tests utilizing both
    
  - SQLDelight
    
- Familiarity with Kotlin and async programming using it.
  
  - How to write safe async functions
    
  - Take advantage of language features like:
    
    - Extensions
      
    - Functional programming via lambdas
      
    - Specialized classes
      
    - Access modifiers
      
- Ability to flexible and write scalable code.
  
  - `buildSrc`, shared `build.gradle`, kotlin DSL
    
  - Utilize modularized design to increase cohesion and decrease strong coupling
    
  - Minimize dependency each module has to bring in to function
    
- Ability to write testable and safe code
  
  - Abstract away implementations whenever possible:
    
    - Prevent strong coupling with a specific library/tech
      
    - Allow easier testing using Fakes
      
    - Clear separation on internal (implementation) vs exposed (interface) codes
      
  - Wrap function results in result monad for smoother error handling.
    
  - Take advantage of logging
    
- MVI Architecture
  
  - Unidirectional dataflow
    
  - Intent -> State constraint allows for clear and easy to debug code
    
  - Logging in reducer allows for realtime and time travel debugging
    

## Android tech stacks and libraries

From the get go, I went all out on this front by exclusively using the latest techs. The entire apps UI, Navigation, automated UI tests, end to end test, dependency injection are done using the Jetpack Compose (+ its navigation and testing libraries) and Dagger Hilt.

I also design the modules with Kotlin Multi-Mobile in mind, so any module that doesn't need android dependency is a pure java module. Hence the choice to use rather verbose SQLDelight in favor of something more android tailored, like ROOM. This would allow easy transformation of those modules into KMM module in the future, allowing iOS project to also utilize them.

In addition to these major libraries, I also utilize few QOL improving libraries such as `kotlin-result` and `Accompanist` to write better code.

Although there are many useful libraries out there, for a large-scale production app, we should be diligent in bringing them in to reduce third party dependency as much as possible. Nothing is worse than having to revert shipped code because some library out there is broken (did someone say Log4J?).

## Kotlin, Async programming

Always suspend long running tasks, and if there is a chance of threading issue, protect it using `actors`. You can see example of this in `data-source-live` and `viewModels`.

I also thought about incorporating `flow` to create a truly observable pattern, but could not think of a way to achieve it here without making the code feel awkward.

One thought was the idea of "active user". Since the database can store multiple users, upon login, we would update a flow of `activeUser: UserProfile` directly in datasource. This value would be a flow, which can be listened to in the `confirmationViewModel`, thus allowing us to "know" who is the logged in individual and also having a benefit of being aware to any changes happening to the profile without doing another `get`.

## Modularization and Ease of testing

From the get-go, I had scalability and flexibility in mind when designing this project. By separating out each features into their own module (ex: `register`, `confirmation`, `data-source`...) multiple team can work on their isolated module without stepping on each other's toes while also allowing for future modification to one module without affect other ones too much.

I had to delete and consolidate android modules halfway through due to time constraints, but the spirit of my intention is still there with modularized java modules (Still works for KMM!).

Having these modular design also helps with testing and controlling code access if we go further and divide each modules into sub-modules of something like: `internal`, `public`, `testing`. This will allow consumers to only pull in test or public modules while protecting internal codes from unnecessary exposure and hiding away the implementation. I attempted to demonstrate this in our `datas-source`.

Also, test the state, not the implementation. So I always preferred on creating fakes rather than using mocking library to mocks. Data source and use-cases are intentionally designed to make this easier.

For UI tests, similar can be said thanks to how easy it is to write them thanks to compose and dagger. You can see my examples in app's androidTest package.

## Architecture

Ever since the debut of Jetpack compose, I've preferred using MVI architecture. Declarative and very state based nature of Jetpack compose and the immutable and unidirectional programming MVI allows matches very nicely.

By placing a logger in reduce, one can basically see the entire state of the screen step by step, making debugging easier.

The unidirectional data flow also makes writing safe code easier. Since there is one way into changing the state and one way out for that state to come back, when coupled with well defined uses cases, writing safe code becomes very easy.

Testing is also made easy since we can just feed states into the viewModel/composable and check the resulting state.

## What can be improved

It is hard for this app to be any more insecure. I initially planned to at least hash+salt the password before storing to the database. However, I decided against it since we should never be storing it in local device to begin with, and I wanted to use the time to work on other aspects of the project.

You may notice there is a `longLiveToken` in user domain object. It was meant to emulate/allow biometric login, but I did not have time to implementing the "Sign In" button/screen. Idea was to have a artificial "Token" somewhere in the repo, and use that as a long live token from server. We would encrypt it with cipher associated with the biometric authentication, and when user tried to login, we would use decrypt cipher to get the original token back. And if the original match the result, we login.

Database also can use more work as in the insert operation is kind of rough and overwrites any entry. Ideally we want distinct operations with clear happy/unhappy cases so we can properly handle all situations.

Testing - there can always be more testing. With more time, I would have liked to add through test for all modules, and their implementations.

UI/UX/AX - there is no accessibility to speak of at the moment and I'm sure the design can use some help as well. For a prod app, we want to make sure the app is accessible to all users and ideally have testing for talkback and other AX services. We also want to keep in mind screen rotations and various screen dimensions android ecosystem offers.

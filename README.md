# MusicManager
App for managing favourite albums. Excercise based on a task with requirements.

Estimated Time until all requirements were fulfilled: 40-45 hours (including time spent on reading about different frameworks and problems)
Breakdown of the days:
⋅⋅* Monday, 24.12: 8-10 hours (around 5h trying to create a Create a CustomConverter and its factory to unwrap the JSON-Response, both were discarded and replaced with a custom Interceptor)
..* Tuesday, 25.12: 8-10 hours (around 4 hours reading up on Navigation Component and Dagger to solve the dependency problem in Repository and ViewModel. Dagger did not end up being used in the project)
..* Wednesday, 26.12: 8-10 hours (around 3 hours to solve the fact, that the API is not consistent, in the instances of returned Albums. Solution: custom JSONDeserializer, more information on this as a comment above the Deserializer-class)
..* Thursday, 27.12: 8-10 hours (around 3 hours to solve a databinding problem in AlbumDetailFragment, more details can be found as a comment in the class)
..* Friday, 28.12: 5 hours 
..* Saturday, 29.12: 30 minutes for introducing SingleLiveEvent-class

Remarks on design decisions:
..* AlbumRepository is a classical Singleton with getInstance()-method. Another solution would have been using Kotlins Object-class with an init()-Method which is called in an extended application class. Looking back, the latter might have been the better decision, since the Viewmodels would not require the injection of the repository-dependency through the ViewModelFactory in that case.

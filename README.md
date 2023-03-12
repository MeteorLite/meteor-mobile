# meteor-mobile  
Android port of https://github.com/MeteorLite/meteor-client
  
Current features:  
  
Injector works  
Full AWT software rendering (50 fps unless your resolution is absurd)  
Logging in / world interacting  
osrs.Reflection checks (required for jagex to not input block you)  
Touch Support (some issues still but usable)  
Keyboard Support (for login, in-game coming soon)  
NDK (not used yet but is supported)

## Building  
  
Install Android Studio/NDK and import the project.  
(minimum api 26)  
  
## Running  
  
You MUST disable bytecode verification for debuggable apps in android developer settings to run currently  
  
![image](https://user-images.githubusercontent.com/2943260/224528317-6e3318fd-4bbf-4b4a-9953-1f33c22a8693.png)

  
Run configurations are bundled into the project.

Plug in you android device, or use an emulated device

Run `Inject` first run, or when updating api / mixins

Finally, Run `Run Meteor Mobile`
  
## Contributing  
  
Please do.

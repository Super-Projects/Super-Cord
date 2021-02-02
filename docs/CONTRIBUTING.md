# Contributing Guidelines
Here you will find all the guidelines for working on Super-Cord. If you have any questions or suggestions for changes, please create an issue or contact a repository administrator.
<br>
<br>
<br>
## Translation
If you have found spelling mistakes or errors in the translation, please feel free to change them. Please note that only meaningful contributions will be accepted.

## Participate in Super-Cord as a contributor
To participate in this project you should have some knowledge of the different programming languages. We don't give you an exact skill level, but you should decide for yourself to what extent you think you can make constructive contributions. 

**Please take a moment to read through and understand the guidelines.** This is important so that you can work effectively and we can also incorporate your contributions.

## The issue tracker
#### Labels
+ `report system` - Problems with the reporting system. Either in the web interface or on the plugin page
+ `player system` - Problems with the player system
+ `ban system` - Problems with the ban system
+ `permission system` - Problems with the reporting system, only ingame
+ `server system` - Problems with the server system
+ `documentation` - Problems or errors with the documentation. May also be a wrong definition or incomprehensible explanation
+ `bug` - Bugs ingame or in the interface
+ `enhancement` - Proposals for new or existing modules or features
+ `web interface` - Problems with the webinterface
+ `css` - Poor layout, poor design, problems with presentation

## Pull requests
Good pull requests - `corrections, improvements, new functions` - are a great help. They should always stay focused and not contain unrelated commits.

Please ask first, before you start working on larger pull requests (e.g. if it's about new features, or you want to rewrite or port code). Otherwise, you will spend a lot of time on something that the developers of the project may not want to include.

The best way to get your work integrated into the project is to follow the process below:

  1. [Fork](https://help.github.com/fork-a-repo/ the project, clone your fork locally and set up the remote URLs:
    
     ```bash
     # Clone the fork of the repository into the current directory
     git clone https://github.com/<your-username>/super-cord.git
     # Navigate to the new directory
     cd super-cord
     # Set up a remote URL "upstream" to the original repository
     git remote add upstream https://github.com/Super-Projects/Super-Cord
     ```
  
  2. If it's been a while since cloning, get the latest changes:
    
     ```bash
     git checkout master
     git pull upstream master
     ```
  
  3. Create a new theme branch to include your function, change or correction:
    
     ```bash
     git checkout -b <thema-branch-name>
     ```
  
  4. Commit your changes in logical chunks. Please follow the [commit message guidelines](http://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html) or your code will probably not be included in the main project. Use gits [interactive rebase mode](https://help.github.com/articles/interactive-rebase), to clean up your commits before you publish them.
   
  5. Merge (or rebase) the development branch from the upstream into your theme branch:

     ```bash
     git pull [--rebase] upstream master
     ```
  
  6. Push your theme branch up into your fork:

     ```bash
     git push origin <thema-branch-name>
     ```

**IMPORTANT: When you submit a correction, you are allowing the project owners to license your work under the terms of the [Creative Commons Zero v1.0 Universal](https://github.com/Super-Projects/Super-Cloud/LICENSE.md) License.

## Licence

By submitting your code contribution, you agree to license your contribution under the
the [Creative Commons Zero v1.0 Universal](https://github.com/Super-Projects/Super-Cord/LICENSE.md) license.

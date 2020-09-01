#  A discord bot

A Java bot for [Discord](https://discordapp.com/) using the [JDA library](https://github.com/DV8FromTheWorld/JDA) and the [Spring framework](https://spring.io/projects/spring-framework).

It uses postgresSQL to store data. 

## What can it do?

* A lot of commands
* Play games (in development)
* Experience system
* Customizable censorship filter

### Commands

Commands are prefixed with a "!".
For a list of commands in discord the **help** command can be used.

Current list of all available commands. See below for a more detailed list

Commands | | | 
--- | --- | ---| 
[about](#about) | [create channel](#create) | [debug](#debug) |
[help](#help) | [profile](#profile) |[xp](#xp) |

## Games (in development)

Games can be accessed though the **!game** command

A list of games:

Key | Name | Players |
--- | --- | --- |
gos | Game of sticks | 2
tic | Tic tac toe | 2


## Experience system

Every time the user types a message he receives an experience. Receiving time can be configured in properties. 

## Censorship filter

Every time the user enters a message containing words from bot.censor list in application.yaml, the bot deletes this message with warning.

## Run the bot 

1. Create a database
  
2. Clone the project with git

3. Insert your token in src\main\resources\application.yaml

4. Collect dependencies by `mvn clean install`
    
5. Run

## Command details

### about

Shows some general information about this bot.

Aliases: about

### create

Creates new channel with $channel_name in category with $category_name (or in main category by default)

Aliases: create channel <category_name> <channel_name>

### debug

Enables/disables debug mode

Aliases: debug on/off

### help

An attempt to help out and show list of command

Aliases: help

### profile

Shows info about user

Aliases: profile

### xp

Shows experience of user

Aliases: xp

## Configuration

The configuration is stored in the application.yaml file.




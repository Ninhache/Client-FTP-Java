# Client Console
> Client Graphique :
https://gitlab.univ-lille.fr/neo.almeida.etu/m4105c-project-2

## Compiler et exécuter le logiciel
> Ce projet nécessite Maven.

Lancer le client FTP
```sh
mvn compile exec:java <hôte> <port>
```

## Commandes
|Commande client|Description|
|--|--|
|`LOGIN`|Login to a specified user.|
|`HELP`|Returns usage documentation on a command if specified, else a general help document is returned.|
|`SVHP`|Displays the server help page for a command if specified or a list of commands.|

|Commande FTP|Description|
|--|--|
|`CDUP`|Change to Parent Directory.|
|`CWD`|Change working directory.|
|`LIST`|Displays the content of a directory with detailed informations.|
|`MKD`|Create a new directory.|
|`MODE`|Sets the transfer mode (Stream, Block, or Compressed).|
|`NLST`|Lists the names of files and folders in a directory.|
|`NOOP`|No operation (dummy packet; used mostly on keepalives).|
|`PASS`|Authentication password.|
|`PASV`|Enter passive mode.|
|`PORT`|Specifies an address and port to which the server should connect.|
|`PWD`|Print working directory. Returns the current directory of the host.|
|`QUIT`|Disconnect.|
|`REIN`|Reinitialize the FTP session (log out from the current session).|
|`RETR`|Download a file from the FTP server on the local computer.|
|`RMD`|Delete a directory on the server.|
|`SIZE`|Print the size of a file on the server in bytes.|
|`STOR`|Upload a file on the server.|
|`STRU`|Set file transfer structure.|
|`TYPE`|Sets the transfer mode (ASCII, Binary or EBCDIC).|
|`USER`|Authentication username.|
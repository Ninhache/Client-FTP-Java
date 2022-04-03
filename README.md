# Client Console
> Client Graphique :
https://gitlab.univ-lille.fr/neo.almeida.etu/m4105c-project-2

## Compiler et exécuter le logiciel
> Ce projet nécessite Maven.

Lancer le client FTP
```sh
mvn compile exec:java
```

## Commandes
|Commande client|Syntaxe|Description|
|--|--|--|
|`LOGIN`|`LOGIN <username> <password>`|Login to a specified user.|
|`DATA`|`DATA <A\|P>`|Select between active and passive mode for data transfer.|
|`HELP`|`HELP [command]`|Returns usage documentation on a command if specified, else a general help document is returned.|
|`SVHP`||Displays the server help page for a command if specified or a list of commands.|

|Commande FTP|Syntaxe|Description|
|--|--|--|
|`CDUP`|`CDUP`|Change to Parent Directory.|
|`CWD`|`CWD <directory>`|Change working directory.|
|`LIST`|`LIST [directory]`|Displays the content of a directory with detailed informations.|
|`MKD`|`MKD <directory name>`|Create a new directory.|
|`MODE`|`MODE <S\|B\|C>`|Sets the transfer mode (Stream, Block, or Compressed).|
|`NLST`||Lists the names of files and folders in a directory.|
|`NOOP`|`NOOP`|No operation (dummy packet; used mostly on keepalives).|
|`PASS`|`PASS <password>`|Authentication password.|
|`PASV`|`PASSV`|Enter passive mode.|
|`PORT`|`PORT`|Specifies an address and port to which the server should connect.|
|`PWD`|`PWD`|Print working directory. Returns the current directory of the host.|
|`QUIT`|`QUIT`|Disconnect.|
|`REIN`|`REIN`|Reinitialize the FTP session (log out from the current session).|
|`RETR`|`RETR <server file> [local destination]`|Download a file from the FTP server on the local computer.|
|`RMD`|`RMD <server file>`|Delete a directory on the server.|
|`SIZE`|`SIZE <server file>`|Print the size of a file on the server in bytes.|
|`STOR`|`STOR <local file> [server destination]`|Upload a file on the server.|
|`STRU`|`STRU <F\|R\|P>`|Set file transfer structure. (File, Record or Page)|
|`TYPE`|`TYPE <A\|I\|E>`|Sets the transfer mode (ASCII (A), Binary(I) or EBCDIC(E)).|
|`USER`|`USER <username>`|Authentication username.|

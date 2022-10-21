# Objective

We are going to build an example server/client where the client sends a request to the server and
receives a response. The server will represent a word searcher functionality where it loads in a large text
file and searches for occurrences of a word or phrase within the array of strings. It responds with a list of
integers which represent each line number that the word/phrase appeared in.  
You will construct two applications: a client and a server. The client will contain a GUI where a user can
type in a word or phrase and press a button to connect to the server. The client will display the list of
results into a JList on the GUI. The server will accept connections and process the word search,
responding with a list of integers for the client to then process. 
# json-parser

An attempt to create a custom json-parser.

Parses and returns Objects in form of **CustomPair**

````
class CustomPair {
   public ObjectType objectType;
   public Object object;
}

````

````
enum ObjectType {
    STRING, 
    NUMBER, 
    CUSTOM_OBJECT
}

````

**Supported Data Types**
 1. STRING -> characterised by  ' and ' (single quote at start and end)
 2. CUSTOM_OBJECT -> characterised by { and } characters at start and end
 3. NUMBER -> if something not qualified as STRING or CUSTOM_OBJECT
 
**Usage**

run the main functiion!
 
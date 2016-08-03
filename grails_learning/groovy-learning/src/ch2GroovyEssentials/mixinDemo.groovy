package ch2GroovyEssentials

//NOTE: simple example demoing groovy mixin.  Looks like it achieves the same thing as inheritance?
class Greeter {
    String message
    void greet(String name) {
        println message + ' ' + name
        println "'this' class: ${this.getClass()}"           //note how when greet is called on a cowboy, this is a cowboy
    }
}
@Mixin(Greeter)
class Cowboy {
    Cowboy() {
        this.message = "Howdy"
    }
}
def cowboy = new Cowboy()
cowboy.greet("Peter")    //CAN call greet on the cowboy as it has had the Greeter stuff mixed in.




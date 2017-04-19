
// TODO: http://eed3si9n.com/herding-cats/import-guide.html
/*
In Scala, imports are used for two purposes:

To include names of values and types into the scope.
To include implicits into the scope.


Implicits are for 4 purposes that I can think of:

To provide typeclass instances.
To inject methods and operators. (static monkey patching)
To declare type constraints.
To retrieve type information from compiler.



Implicits are selected in the following precedence:

Values and converters accessible without prefix via local declaration, imports, outer scope, inheritance, and current package object. Inner scope can shadow values when they are named the same.
Implicit scope. Values and converters declared in companion objects and package object of the type, its parts, or super types.
 */

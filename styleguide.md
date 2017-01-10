
# Flight Crew 747 Java Style Guide

## Nomenclature

### Variables
#### Variable names should:
* Be descriptive (e.g. not “temp”, “foo”, etc.)
* Avoid abbreviations and avoid acronyms that aren’t standard and commonplace
* Start with a lowercase character and follow camelCase form (e.g. thisIsAVariable)
* Treat acronyms as words (e.g. xmlHttpRequest instead of XMLHTTPRequest

#### Variable declaration:
* Variables should be declared when they are first used (i.e. not all at the top of the function). This makes it easier to find the declaration of a given variable (i.e. less scrolling) and reduces clutter at the top of functions.
* Only one variable should be declared in a single statement, and if possible, variables should be initialized in the declaration.

### Classes
#### Class names should:
*	Start with an uppercase character and follow CamelCase form (e.g. ArmController)
	
### Functions
#### Function names should:
*	Be descriptive and action-oriented (i.e. contain a verb)
*	Start with a lowercase character and follow camelCase form (e.g. processOperatorInputs()) - this is a Java industry standard
*	Treat acronyms as words (e.g. outputHtml() instead of outputHTML())

### Files
#### File names should:
*	Start with an uppercase character and follow CamelCase form (e.g. CommonRoutines.java)
*	Be named the same as the class they contain

### Constants and enumerated data types
Constants should be in upper case, with words separated by an underscore (e.g. MILLIVOLTS_PER_DEGREE). Enumerated data types (enums) should be named normally, but the values they contain should follow this uppercase convention.
For example:
```
enum CompassDirection {
  NORTH,
  SOUTH,
  EAST,
  WEST
}
public static final int NUMBER_OF_WHEELS = 3;
```

## Formatting

### Indentation
An indentation width of 2 spaces should be used. Spaces should always be inserted instead of tab characters – check your editor’s settings.

### Column limit
No line should exceed a width of 100 columns, to reduce the need for scrolling when more than one window is open. Lines that exceed 100 columns should be truncated at an appropriate place and the remainder indented 4 spaces on the following line. Long expressions should preferentially be terminated after an operator, but in a sensible place to preserve readability. For example:
```
// Good
x = ((a && b) || (c && d)) &&
    ((e && f) || (g && !h));
```
```
// Bad
x = ((a && b) || (c && d))
    && ((e && f) || (g && !h));
x = ((a && b) || (c && d)) && ((e && f) || (g &&
    !h));
```
For function definitions and calls that cannot fit on one line, the subsequent arguments should be on lines following the first, indented by the same amount. In other words, if a function and its arguments cannot all fit on the same line, the arguments should each be on a separate line. If any of the arguments cannot fit in that space, the first should go on the next line, indented 4 spaces. For example:
```
// Good
someVariable = aReallyLongFunctionName(aReallyLongFunctionArgument,
                                       anotherArgument,
                                       yetAnotherArgument,
                                       true);
someVariable = aReallyLongFunctionName(
    aReallyReallyReallyLongFunctionArgument,
    anotherArgument,
    yetAnotherArgument,
    true);
```
```
// Bad
someVariable = aReallyLongFunctionName(aReallyLongFunctionArgument,
    anotherArgument, yetAnotherArgument, true);
```

### Curly braces
The opening curly brace ({) should be on the same line as the code preceding it. If there is a subsequent clause (else, else if, etc.) it should go on the same line as the the closing curly brace (}). Curly braces should always be used, even in single line blocks. For example:
```
// Good
if (someVariable < 2) {
  doSomething();
} else {
  doSomethingElse();
}
```
```
// Bad
if (someVariable < 2)
{
  doSomething();
}
else
{
  doSomethingElse();
}
```
```
// Also bad
if (someVariable < 2) { doSomething(); }
```
```
// Also bad
if (someVariable <2)
  doSomething();
```

### Switch statements
Switch statements should be formatted as follows:
```
switch (someVariable) {
  case 1:
    doSomething();
    break;
  case 2:
    doSomethingElse();
    break;
  default:
    doSomethingElseEntirely();
    break;
}
```

### Whitespace
All operators and keywords should be padded with whitespace, and the inside of brackets should not contain extra whitespace:
```
// Good
someVariable = 1 + 2 – 3 * (someOtherVariable >> 8);
for (i = 0; i < 10; i++);
```
```
// Bad
someVariable= 1+2 – 3*(someOtherVariable>>8);
for( i=0;i<10;i++ );
```

There should be one line of whitespace between the closing curly brace of a function and the beginning the next function definition.
```
/**
 * Does all the things.
 */
public boolean foo() {
  return true;
}

/**
 * Does all the other things.
 */
public boolean bar() {
  return false;
}
```

## Commenting

In general, code should be self-commenting (i.e. written in such a clear way, with good variable and function names, that what it does is obvious to an uninvolved observer). Comments should take the form of sentences, with verbs conjugated in the present tense and proper grammar and punctuation.

### Line comments
#### Comments should: 
* be used reserved for lines or blocks of code whose function or purpose is non-obvious
* be located on the line immediately preceding the code, in single-line (//) format. 
* be in the imperative point of view (as opposed to the descriptive – e.g. “Process inputs” instead of “Processes inputs”).

### Class header
All classes should have a header of the following Javadoc format, right above the class declaration:
```
/**
  * [Class description]
  *
  */
```
The description should be in the descriptive point of view (e.g. "Contains functions to deal with memory.") and should have proper capitalization and punctuation.

### Functions
Functions should be preceded by comments in Javadoc style:
```
/**
  * [Function description]
  */
```
* Function descriptions should start with a verb and be in the descriptive point of view (as opposed to the imperative - e.g. “Processes inputs” instead of “Process inputs”). 
* They should describe what the function does, and what parameters it takes and what it returns, if applicable.


/*
Redline Smalltalk is licensed under the MIT License

Redline Smalltalk Copyright (c) 2010 James C. Ladd

Permission is hereby granted, free of charge, to any person obtaining a copy of this software
and associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

Please see DEVELOPER-CERTIFICATE-OF-ORIGIN if you wish to contribute a patch to Redline Smalltalk.
*/
package st.redline.compiler;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognizerSharedState;

/**

 * This is the super class for the lexer. It is extended by the lexer class
 * generated from TLexer.g.
 *
 * Do not place code and declarations in the lexer .g files, use
 * a superclass like this and place all the support methods and
 * error overrides etc in the super class. This way you will keep
 * the lexer grammar clean and hunky dory.
 *
 * @author Jim Idle - Temporal Wave LLC (jimi@idle.ws)
 * @author James Ladd - Redline
 */
public abstract class AbstractPreProcLexer
    extends Lexer

{
    /**
     * Default constructor for the lexer, when you do not yet know what
     * the character stream to be provided is.
     */
    public AbstractPreProcLexer() {
    }

    /**
     * Create a new instance of the lexer using the given character stream as
     * the input to lex into tokens.
     *
     * @param input A valid character stream that contains the ruleSrc code you
     *              wish to compile (or lex at least)
     */
    public AbstractPreProcLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }

    /**
     * Internal constructor for ANTLR - do not use.
     *
     * @param input The character stream we are going to lex
     * @param state The shared state object, shared between all lexer components
     */
    public AbstractPreProcLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }

    /**
     * Used internally by the lexer to build the pre-processed output stream
     */
    StringBuilder sb;
    
    /**
     * Name of the input file
     */
    String methodName;

	boolean haveMethods = false;

	public void haveMethods() {
		haveMethods = true;
	}

	public boolean firstMethod() {
		return !haveMethods;
	}

    /**
     * Initialize the pre-processor according to the input size.
     * 
     */
    public void initPreProc(String name) {
            
        // Assume an expansion of 20%, which is just a guess, but is
        // probably enough for most pre-processing situations.
        //
        sb = new StringBuilder( (int)(input.size() * 1.20));   
        
        // See if we have the file name?
        //
        methodName = name; // input.getSourceName();
        
//        if  (methodName == null || methodName.isEmpty()) {
//
//            methodName = "unknownMethod";
//        }
//        else {
//
//            // Massage the name
//            //
//            methodName = methodName.substring(methodName.lastIndexOf(File.separatorChar)+1); // Remove paths
//
//            if (methodName.lastIndexOf('.') != -1) {
//
//                methodName = methodName.substring(0, methodName.lastIndexOf('.'));
//            }
//        }
    }
   
    /**
     * Provide a reference to the internal StringBuilder in case it needs
     * to be manipulated outside the pre-processor.
     * 
     * @return A reference to the StringBuilder that is building the output
     *         stream.
     */
    public StringBuilder getPreProcBuilder() {
        return sb;
    }
    
    /**
     * Provide the pre-processed input in String form.
     * 
     * @return 
     */
    public String getOutput() {
	    if (haveMethods)
		    sb.append("]");
        return sb.toString();
    }
}

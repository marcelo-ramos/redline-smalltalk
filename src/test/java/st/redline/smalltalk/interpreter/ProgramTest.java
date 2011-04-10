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
*/
package st.redline.smalltalk.interpreter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProgramTest {

	@Mock NodeVisitor visitor;
	@Mock Temporaries temporaries;
	@Mock Statements statements;
	@Mock Methods methods;
	private Program program;

	@Before public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		program = new Program(temporaries, statements, methods);
	}

	@Test public void shouldVisitProgram() {
		program.accept(visitor);
		verify(visitor).visit(program);
	}

	@Test public void shouldVisitTemporariesWhenTemporariesNotEmpty() {
		when(temporaries.isEmpty()).thenReturn(false);
		program.accept(visitor);
		verify(temporaries).accept(visitor);
	}

	@Test public void shouldNotVisitTemporariesWhenTemporariesEmpty() {
		when(temporaries.isEmpty()).thenReturn(true);
		program.accept(visitor);
		verify(temporaries, never()).accept(visitor);
	}

	@Test public void shouldVisitStatementsWhenStatementsNotEmpty() {
		when(statements.isEmpty()).thenReturn(false);
		program.accept(visitor);
		verify(statements).accept(visitor);
	}

	@Test public void shouldNotVisitStatementsWhenStatementsEmpty() {
		when(statements.isEmpty()).thenReturn(true);
		program.accept(visitor);
		verify(statements, never()).accept(visitor);
	}

	@Test public void shouldVisitMethodsWhenMethodsNotEmpty() {
		when(methods.isEmpty()).thenReturn(false);
		program.accept(visitor);
		verify(methods).accept(visitor);
	}

	@Test public void shouldNotVisitMethodsWhenMethodsEmpty() {
		when(methods.isEmpty()).thenReturn(true);
		program.accept(visitor);
		verify(methods, never()).accept(visitor);
	}
}

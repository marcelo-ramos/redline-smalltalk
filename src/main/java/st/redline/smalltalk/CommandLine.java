package st.redline.smalltalk;

import org.apache.commons.cli.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Processes the raw arguments given in String[] format.
 *
 * Command line options are different to command line arguments.
 * Arguments is the list of <source files>
 */
public class CommandLine {

	private static final String SOURCEPATH_OPTION = "sourcepath";

	private final String[] rawArguments;
	private org.apache.commons.cli.CommandLine commandLine;

	public CommandLine(String[] rawArguments) {
		this.rawArguments = rawArguments;
		tryParseArguments();
	}

	String userPath() {
		return System.getProperty("user.dir");
	}

	void printHelp(PrintWriter printWriter) {
		helpFormatter().printHelp(printWriter, 80, "stic [options] <source files>", "", commandLineOptions(), 1, 4, "");
		printWriter.flush();
	}

	List arguments() {
		return commandLine.getArgList();
	}

	boolean verboseRequested() {
		return haveVerboseOption();
	}

	boolean helpRequested() {
		return haveHelpOption() || haveNoArguments();
	}

	boolean haveNoArguments() {
		return commandLine.getArgList().isEmpty();
	}

	boolean haveHelpOption() {
		return commandLine.hasOption('?');
	}

	boolean haveVerboseOption() {
		return commandLine.hasOption('v');
	}

	void tryParseArguments() {
		try {
			commandLine = commandLineParser().parse(commandLineOptions(), rawArguments);
		} catch (ParseException e) {
			throw new IllegalStateException(e);
		}
	}

	Options commandLineOptions() {
		return new CommandLineOptions();
	}

	List<String> sourcePaths() {
		List<String> sourcePaths = new ArrayList<String>();
		if (commandLine.hasOption(SOURCEPATH_OPTION))
			for (String path : commandLine.getOptionValue(SOURCEPATH_OPTION).split(File.pathSeparator))
				sourcePaths.add(path);
		sourcePaths.add(userPath());
		return sourcePaths;
	}

	CommandLineParser commandLineParser() {
		return new GnuParser();
	}

	HelpFormatter helpFormatter() {
		return new HelpFormatter();
	}

	private class CommandLineOptions extends Options {

		public CommandLineOptions() {
			addOption(help());
			addOption(sourcePath());
			addOption(verbose());
		}

		private Option verbose() {
			return new Option("v", "verbose", false, "output messages about what Redline is doing.");
		}

		private Option help() {
			return new Option("?", "help", false, "print this message.");
		}

		private Option sourcePath() {
			return OptionBuilder.withArgName("path")
								.hasArg()
								.withDescription("where to find input source files.")
								.create(SOURCEPATH_OPTION);
		}
	}
}

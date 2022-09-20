<h1> Boggle Solver API</h1>
<h4> developed by Cameron Alberg</h4>
<h2><u>Intro</u></h2>
<div class="section">
  <p>Boggle Solver is a REST app that can solve any provided Boggle board (3x3 up to 6x6),
    as well as provide randomized boards based on actual Boggle die character distributions</p>
  <p>This is a Spring Boot application containerized with Docker and self-hosted by me (Cameron).</p>
  <p>This API is accessible publicly - an example usage with an interactive frontend is available here</p>
</div>
<h2><u>Usage</u></h2>
<div class="section">
  <p>There are two available endpoints for the Boggle Solver API:</p>
  <h3>/shuffle</h3>
  <p>The /shuffle endpoint takes a integer <code>boardSize</code> parameter between 3 and 6, representing the height and length of the square board. If no size is provided, a default size of 4 is used. </p>
  <p>It returns a JSON object with an alphabetic string of length boardSize<sup>2</sup>, which can be used to display a Boggle Board. </p>
  <h3>/solve</h3>
  <p>The /solve endpoint takes a string <code>board</code> parameter of alphabetic characters representing a Boggle Board and returns a list of all found words within the board.</p>
  <p>If the provided string cannot be constructed into a Boggle Board, an error is returned</p>
  <p>Additional metrics are provided with the found words, such as total words found, total Boggle score, and elapsed time searching (in ms).</p>
</div>
<h2><u>Getting Started</u></h2>
<div class="section">
  <p>This application can be used via a JAR file or Docker</p>
  <h3>JAR File</h3>
  <p><a href="https://www.oracle.com/java/technologies/downloads/#java11">Java 11</a> is required to run this application via a JAR File.</p>
  <p>The latest release of the Boggle Solver API can be downloaded as a .jar package <a href="https://github.com/cameronalberg/boggle/releases">here</a></p>
  <p>A default dictionary is provided, but a custom one can be taken during 
application startup. The <code>DATABASE</code> and optional 
<code>DATABASE_DEFAULTPATH</code> environment variables can be set to define 
a custom dictionary file. The file can be any text file of words 
(each on a new line), which the application will use when searching a board. 
If no path is provided, it is expected in a root directory called 
<code>data</code>.
</p>
  <p>Sample dictionaries are available <a href="https://github.com/cameronalberg/boggle/tree/main/src/main/resources/data">here</a> </p>
  <p>If Java is installed, the application can be started with the following command: </p>
  <code>java -jar ./{latestReleaseName}.jar</code>
  <p>The application runs on port 8080. If port 8080 is not available, installation via Docker is recommended.</p>
  <h3>Docker</h3>
  <p>The <a href="https://docs.docker.com/engine/install/">Docker engine</a> is required to run this application through Docker</p>
  <p>The latest docker image can be retrieved using the following command:</p>
  <code>docker pull calberg/boggle-spring-docker</code>
  <p>The image can be run as a container with the following command:</p>
  <code>docker run -p {host_port}:8080 calberg/boggle-spring-docker</code>
  <p><code>host_port</code> can be 8080 or another port number if 8080 is already taken.</p>
  <p>A default dictionary is provided in the Docker image. If another dictionary is preferred, the run command should be modified: </p>
  <code>docker run -p {host_port}:8080 -v {dictionaryFileDirectory}:./data -e DATABASE={txtFileName} calberg/boggle-spring-docker</code>
  <p><code>dictionaryFileDirectory</code> is a folder on the host machine where your {txtFileName} dictionary file exists.</p>
</div>

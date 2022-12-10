import org.junit.*;
import org.junit.rules.Timeout;

// me
import java.util.*;
import java.io.*;
// me
import java.util.concurrent.TimeUnit;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

import org.junit.rules.Timeout;
import java.util.concurrent.TimeUnit;

public class CustomTest {
  private int reg_sp = 0;
  private int reg_s0 = 0;
  private int reg_s1 = 0;
  private int reg_s2 = 0;
  private int reg_s3 = 0;
  private int reg_s4 = 0;
  private int reg_s5 = 0;
  private int reg_s6 = 0;
  private int reg_s7 = 0;


  /**
   * @param filename
   * @return double[] of numbers in a file matrix
   * @throws FileNotFoundException
   */
  public static double[] getInputsDouble(String filename) throws FileNotFoundException {
    ArrayList<Double> retarrl = new ArrayList<Double>();
    File currentDir = new File(".");
    File parentDir = currentDir.getParentFile();
    File file = new File(parentDir, filename);
    Scanner scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      String[] line = scanner.nextLine().split(" ");
      for (String i : line) {
        retarrl.add(Double.parseDouble(i));
      }
    }
    double[] arrayRet = new double[retarrl.size()];
    for (int i = 0; i < retarrl.size(); i++) {
      arrayRet[i] = retarrl.get(i);
    }
    // System.out.println(Arrays.toString(arrayRet));
    return arrayRet;
  }
  /**
   * @param filename
   * @return int[] of numbers in a file matrix
   * @throws FileNotFoundException
   */
  public static int[] getInputs(String filename) throws FileNotFoundException {
    ArrayList<Integer> retarrl = new ArrayList<Integer>();
    File currentDir = new File(".");
    File parentDir = currentDir.getParentFile();
    File file = new File(parentDir, filename);
    Scanner scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      String[] line = scanner.nextLine().split(" ");
      for (String i : line) {
        if (!i.equals(""))
        retarrl.add(Integer.parseInt(i));
      }
    }
    int[] arrayRet = new int[retarrl.size()];
    for (int i = 0; i < retarrl.size(); i++) {
      arrayRet[i] = retarrl.get(i);
    }
    // System.out.println(Arrays.toString(arrayRet));
    return arrayRet;
  }

  /**
   * @param filename
   * @return total number of chars
   * @throws FileNotFoundException
   */
  public static int getCharCount(String filename) throws FileNotFoundException {
    int total = 0;
    File currentDir = new File(".");
    File parentDir = currentDir.getParentFile();
    File file = new File(parentDir, filename);
    Scanner scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      total++; // newline char
      String line = scanner.nextLine();
      total += line.length();
    }
    return total;
  }
  public String getFileContents(String filename) throws FileNotFoundException {
    File currentDir = new File(".");
    File parentDir = currentDir.getParentFile();
    File file = new File(parentDir, filename);
    String file1 = "";
    Scanner scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      file1 += line;
    }
    return file1;
  }

  @Before
  public void preTest() {
    this.reg_s0 = get(s0);
    this.reg_s1 = get(s1);
    this.reg_s2 = get(s2);
    this.reg_s3 = get(s3);
    this.reg_s4 = get(s4);
    this.reg_s5 = get(s5);
    this.reg_s6 = get(s6);
    this.reg_s7 = get(s7);
    this.reg_sp = get(sp);
  }

  @After
  public void postTest() {
    Assert.assertEquals("Register convention violated $s0", this.reg_s0, get(s0));
    Assert.assertEquals("Register convention violated $s1", this.reg_s1, get(s1));
    Assert.assertEquals("Register convention violated $s2", this.reg_s2, get(s2));
    Assert.assertEquals("Register convention violated $s3", this.reg_s3, get(s3));
    Assert.assertEquals("Register convention violated $s4", this.reg_s4, get(s4));
    Assert.assertEquals("Register convention violated $s5", this.reg_s5, get(s5));
    Assert.assertEquals("Register convention violated $s6", this.reg_s6, get(s6));
    Assert.assertEquals("Register convention violated $s7", this.reg_s7, get(s7));
    Assert.assertEquals("Register convention violated $sp", this.reg_sp, get(sp));
  }

  @Rule
  public Timeout timeout = new Timeout(30000, TimeUnit.MILLISECONDS);

  @Test
  public void invalid_data_1() {
    // BufferedInputStream buffer1 = new BufferedInputStream(buffer1, 0);
    Label inpt = asciiData(true, "inputs/badup.txt");
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    // System.out.println(get(a0));
    Assert.assertEquals(-1, get(v0));
  }

  @Test
  public void invalid_file() {
    Label inpt = asciiData(true, "this_does_not_exist.txt");
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    Assert.assertEquals(-1, get(v0));
  }

  // @Test // PART 1: "You should assume the buffer contains all zeros before
  // initialization"
  // public void invalid_buffer() {
  // Label inpt = asciiData(true, "this_does_not_exist.txt");
  // // row, col, matrix
  // Label buffer = asciiData(true, "this is not a valid buffer lol");
  // run("initialize", inpt, buffer);
  // Assert.assertEquals(-1, get(v0));
  // }

  @Test
  public void read_buffer_1() throws FileNotFoundException {
    // BufferedInputStream buffer1 = new BufferedInputStream(buffer1, 0);
    Label inpt = asciiData(true, "inputs/input1.txt");
    int[] input1 = getInputs("inputs/input1.txt");
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    // Label finale = wordData(2,3, 12,3,24,45,6,17);
    // System.out.println(finale.toString());
    // for (int i=0; i<input1.length; i++) System.out.println(getWord(buffer,i*4));
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertEquals(1, get(v0));
  }
  @Test
  public void read_buffer_2() throws FileNotFoundException {
    String file = "inputs/input2.txt";
    Label inpt = asciiData(true, file);
    int[] input1 = getInputs(file);
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertEquals(1, get(v0));
  }
  @Test
  public void read_buffer_3() throws FileNotFoundException {
    String file = "inputs/input3.txt";
    Label inpt = asciiData(true, file);
    int[] input1 = getInputs(file);
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertEquals(1, get(v0));
  }
  @Test
  public void read_buffer_4() throws FileNotFoundException {
    String file = "inputs/input4.txt";
    Label inpt = asciiData(true, file);
    int[] input1 = getInputs(file);
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertEquals(1, get(v0));

  }
  @Test
  public void read_buffer_5() throws FileNotFoundException {
    String file = "inputs/input5.txt";
    Label inpt = asciiData(true, file);
    int[] input1 = getInputs(file);
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertEquals(1, get(v0));
  }
  @Test
  public void read_buffer_6() throws FileNotFoundException {
    String file = "inputs/input6.txt";
    // NEGATIVE NUMBER // FIX //
    Label inpt = asciiData(true, file);
    int[] input1 = getInputs(file);
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    for (int i=0; i<input1.length; i++) Assert.assertEquals(0, getWord(buffer,i*4));
    Assert.assertEquals(-1, get(v0));
  }
  @Test
  public void read_buffer_7() throws FileNotFoundException {
    String file = "inputs/input7.txt";
    double[] input1 = getInputsDouble(file);
    // this file has a decimal
    Label inpt = asciiData(true, file);
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    for (int i=0; i<input1.length; i++) Assert.assertEquals(0, getWord(buffer,i*4));
    Assert.assertEquals(-1, get(v0));
  }
  // fix broken mips
  @Test
  public void read_buffer_8() throws FileNotFoundException {
    String file = "inputs/input8.txt";
    Label inpt = asciiData(true, file);
    int[] input1 = getInputs(file);
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);    
    Assert.assertEquals(-1, get(v0));
    for (int i=0; i<input1.length; i++) Assert.assertEquals(0, getWord(buffer,i*4));
  }
  @Test
  public void read_buffer_9() throws FileNotFoundException {
    String file = "inputs/input9.txt"; // THERE ARE TWO SPACES IN COL
    Label inpt = asciiData(true, file);
    int[] input1 = getInputs(file);
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    Assert.assertEquals(-1, get(v0));
    for (int i=0; i<input1.length; i++) Assert.assertEquals(0, getWord(buffer,i*4));
  }
  @Test
  public void read_buffer_10() throws FileNotFoundException {
    String file = "inputs/input10.txt";
    Label inpt = asciiData(true, file);
    int[] input1 = getInputs(file);
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    for (int i=0; i<input1.length; i++) Assert.assertEquals(0, getWord(buffer,i*4));
    Assert.assertEquals(-1, get(v0));
  }
  @Test
  public void read_buffer_11() throws FileNotFoundException {
    String file = "inputs/input11.txt";
    Label inpt = asciiData(true, file);
    int[] input1 = getInputs(file);
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertEquals(1, get(v0));
  }
  @Test
  public void read_buffer_12() throws FileNotFoundException {
    String file = "inputs/input12.txt";
    Label inpt = asciiData(true, file);
    int[] input1 = getInputs(file);
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    // for (int i=0; i<input1.length; i++) System.out.print(getWord(buffer,i*4) + " ");
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertEquals(1, get(v0));
  }
  @Test
  public void read_buffer_13() throws FileNotFoundException {
    String file = "inputs/input13.txt";
    Label inpt = asciiData(true, file);
    int[] input1 = getInputs(file);
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    // for (int i=0; i<input1.length; i++) System.out.print(getWord(buffer,i*4) + " ");
    for (int i=0; i<input1.length; i++) Assert.assertEquals(0, getWord(buffer,i*4));
    Assert.assertEquals(-1, get(v0));
  }
  @Test
  public void read_buffer_14() throws FileNotFoundException {
    String file = "inputs/bigint.txt";
    Label inpt = asciiData(true, file);
    int[] input1 = getInputs(file);
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertEquals(1, get(v0));
  }
  @Test
  public void write_buffer_1() throws FileNotFoundException {
    String file = "outputs/out.txt";
    Label inpt = asciiData(true, file);
    // row, col, matrix
    Label buffer = wordData(2, 2, 1, 2, 3, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("write_file", inpt, buffer);
    int[] input1 = {2,2,1,2,3,2};
    int[] input2 = getInputs(file);
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertArrayEquals(input1, input2);
    Assert.assertEquals(getCharCount(file), get(v0));
  }
  @Test
  public void write_buffer_2() throws FileNotFoundException {
    // test big number
    String file = "outputs/out.txt";
    Label inpt = asciiData(true, file);
    // row, col, matrix
    Label buffer = wordData(2, 2, 1, 100, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("write_file", inpt, buffer);
    int[] input1 = {2,2,1,100,3,4};
    int[] input2 = getInputs(file);
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertArrayEquals(input1, input2);
    Assert.assertEquals(getCharCount(file), get(v0));
  }
  @Test
  public void write_buffer_3() throws FileNotFoundException {
    // test big number
    String file = "outputs/out.txt";
    Label inpt = asciiData(true, file);
    // row, col, matrix
    Label buffer = wordData(2, 5, 1, 10085, 3, 4, 9, 9, 238965, 18, 0, 1200, 0, 909, 0, 750, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("write_file", inpt, buffer);
    int[] input1 = {2, 5, 1, 10085, 3, 4, 9, 9, 238965, 18, 0, 1200};
    int[] input2 = getInputs(file);
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertArrayEquals(input1, input2);
    Assert.assertEquals(getCharCount(file), get(v0));
  }
  @Test
  public void write_buffer_4() throws FileNotFoundException {
    // test big number
    String readfile = "inputs/input11.txt";
    String outfile = "outputs/out.txt";
    Label out_inpt = asciiData(true, outfile);
    Label in_inpt = asciiData(true, readfile);
    int[] input1 = getInputs(readfile);
    // row, col, matrix
    Label buffer = wordData(10, 10, 1, 10085, 3, 4, 9, 9, 238965, 18, 0, 1200, 0, 909, 0, 750, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", in_inpt, buffer);
    // for (int i=0; i<input1.length; i++) System.out.print(getWord(buffer,i*4) + " ");
    Assert.assertEquals(1, get(v0));
    Label new_buffer = wordData(getWords(buffer.address(), 200)); //2+(input1[0]*input1[1])));
    run("write_file", out_inpt, new_buffer);
    int[] input2 = getInputs(outfile);    
    // System.out.println();
    // for (int i=0; i<input1.length; i++) System.out.print(getWord(new_buffer,i*4) + " ");
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(new_buffer,i*4));
    Assert.assertArrayEquals(input1, input2);
    Assert.assertEquals(getFileContents(readfile), getFileContents(outfile));
    Assert.assertEquals(getCharCount(outfile), get(v0));
  }
  @Test
  public void write_buffer_5() throws FileNotFoundException {
    // test big number
    String readfile = "inputs/input3.txt";
    String outfile = "outputs/out.txt";
    Label out_inpt = asciiData(true, outfile);
    Label in_inpt = asciiData(true, readfile);
    int[] input1 = getInputs(readfile);
    // row, col, matrix
    Label buffer = wordData(10, 10, 1, 10085, 3, 4, 9, 9, 238965, 18, 0, 1200, 0, 909, 0, 750, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", in_inpt, buffer);
    // for (int i=0; i<input1.length; i++) System.out.print(getWord(buffer,i*4) + " ");
    Assert.assertEquals(1, get(v0));
    Label new_buffer = wordData(getWords(buffer.address(), 200)); //2+(input1[0]*input1[1])));
    run("write_file", out_inpt, new_buffer);
    int[] input2 = getInputs(outfile);    
    // System.out.println();
    // for (int i=0; i<input1.length; i++) System.out.print(getWord(new_buffer,i*4) + " ");
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(new_buffer,i*4));
    Assert.assertArrayEquals(input1, input2);
    Assert.assertEquals(getFileContents(readfile), getFileContents(outfile));
    Assert.assertEquals(getCharCount(outfile), get(v0));
  }
  @Test
  public void write_buffer_6() throws FileNotFoundException {
    // test big number
    String readfile = "inputs/input2.txt";
    String outfile = "outputs/out.txt";
    Label out_inpt = asciiData(true, outfile);
    Label in_inpt = asciiData(true, readfile);
    int[] input1 = getInputs(readfile);
    // row, col, matrix
    Label buffer = wordData(10, 10, 1, 10085, 3, 4, 9, 9, 238965, 18, 0, 1200, 0, 909, 0, 750, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", in_inpt, buffer);
    // for (int i=0; i<input1.length; i++) System.out.print(getWord(buffer,i*4) + " ");
    Assert.assertEquals(1, get(v0));
    Label new_buffer = wordData(getWords(buffer.address(), 200)); //2+(input1[0]*input1[1])));
    run("write_file", out_inpt, new_buffer);
    int[] input2 = getInputs(outfile);    
    // System.out.println();
    // for (int i=0; i<input1.length; i++) System.out.print(getWord(new_buffer,i*4) + " ");
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(new_buffer,i*4));
    Assert.assertArrayEquals(input1, input2);
    Assert.assertEquals(getFileContents(readfile), getFileContents(outfile));
    Assert.assertEquals(getCharCount(outfile), get(v0));
  }
  // WE SHOULD ASSUME THE BUFFER IS VALID
  // @Test
  // public void write_buffer_4() throws FileNotFoundException {
  //   // test big number
  //   String file = "outputs/out3.txt";
  //   Label inpt = asciiData(true, file);
  //   // row, col, matrix
  //   Label buffer = wordData(2, 14, 1, 10085, 3, 4, 9, 9, 238965, 18, 0, 1200, 0, 909, 0, 750, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
  //       0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
  //       0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
  //   run("write_file", inpt, buffer);
  //   // int[] input1 = {2, 5, 1, 10085, 3, 4, 9, 9, 238965, 18, 0, 1200};
  //   // int[] input2 = getInputs(file);
  //   // for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
  //   // Assert.assertArrayEquals(input1, input2);
  //   Assert.assertEquals(-1, get(v0));
  // }
  @Test
  public void rotate_90_1() throws FileNotFoundException {
    String file = "outputs/out.txt";
    Label inpt = asciiData(true, file);
    // row, col, matrix
    Label buffer = wordData(3, 3, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1200, 0, 909, 0, 750, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("rotate_clkws_90", buffer, inpt);
    int[] input1 = {3,3, 7, 4, 1, 8, 5, 2, 9, 6, 3};
    int[] input2 = getInputs(file);
    // for (int i=0; i<input1.length; i++) System.out.println(getWord(buffer,i*4));
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertArrayEquals(input1, input2);
    Assert.assertEquals(1, get(v0));
  }
  @Test
  public void rotate_90_2() throws FileNotFoundException {
    String file = "outputs/out.txt";
    Label inpt = asciiData(true, file);
    // row, col, matrix
    Label buffer = wordData(4, 4,1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("rotate_clkws_90", buffer, inpt);
    int[] input1 = {4,4, 13, 9, 5, 1, 14, 10, 6, 2, 15, 11, 7, 3, 16, 12, 8, 4};
    int[] input2 = getInputs(file);
    // for (int i=0; i<input1.length; i++) System.out.println(getWord(buffer,i*4));
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertArrayEquals(input1, input2);
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void rotate_90_3() throws FileNotFoundException {
    String file = "outputs/out.txt";
    Label inpt = asciiData(true, file);
    // row, col, matrix
    Label buffer = wordData(2, 3, 12, 3, 24,45, 6, 17, 0, 909, 0, 750, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("rotate_clkws_90", buffer, inpt);
    int[] input1 = {3,2, 45, 12, 6, 3, 17, 24};
    int[] input2 = getInputs(file);
    // for (int i=0; i<input1.length; i++) System.out.println(getWord(buffer,i*4));
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertArrayEquals(input1, input2);
    Assert.assertEquals(1, get(v0));
  }
  @Test
  public void rotate_90_4() throws FileNotFoundException {
    // test big number
    String file = "outputs/out.txt";
    Label inpt = asciiData(true, file);
    // row, col, matrix
    Label buffer = wordData(3, 5, 99, 19, 12, 2147483647, 9, 73284, 45, 1, 0, 0, 2234, 0, 536870912, 95874398, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("rotate_clkws_90", buffer, inpt);
    int[] input1 = {5, 3, 2234, 73284, 99, 0, 45, 19,536870912, 1, 12,95874398, 0, 2147483647,0, 0, 9};
    int[] input2 = getInputs(file);
    // for (int i=0; i<input1.length; i++) System.out.println(getWord(buffer,i*4));
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertArrayEquals(input1, input2);
    Assert.assertEquals(1, get(v0));
  }
  @Test
  public void rotate_180_1() throws FileNotFoundException {
    String file = "outputs/out.txt";
    Label inpt = asciiData(true, file);
    // row, col, matrix
    Label buffer = wordData(2, 3, 12, 3, 24,45, 6, 17, 0, 909, 0, 750, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("rotate_clkws_180", buffer, inpt);
    int[] input1 = {2,3, 17, 6, 45, 24, 3, 12};
    int[] input2 = getInputs(file);
    // for (int i=0; i<input1.length; i++) System.out.println(getWord(buffer,i*4));
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertArrayEquals(input1, input2);
    Assert.assertEquals(1, get(v0));
  }
  @Test
  public void rotate_270_1() throws FileNotFoundException {
    String file = "outputs/out.txt";
    Label inpt = asciiData(true, file);
    // row, col, matrix
    Label buffer = wordData(2, 3, 12, 3, 24,45, 6, 17, 0, 0, 0, 750, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("rotate_clkws_270", buffer, inpt);
    int[] input1 = {3,2, 24, 17, 3, 6, 12, 45};
    int[] input2 = getInputs(file);
    // for (int i=0; i<input1.length; i++) System.out.print(getWord(buffer,i*4) +" ");
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertArrayEquals(input1, input2);
    Assert.assertEquals(1, get(v0));
  }
  @Test
  public void rotate_270_2() throws FileNotFoundException {
    String file = "outputs/out.txt";
    Label inpt = asciiData(true, file);
    // row, col, matrix
    Label buffer = wordData(3, 3, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1200, 0, 909, 0, 750, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("rotate_clkws_270", buffer, inpt);
    int[] input1 = {3,3, 3,6,9,2,5,8,1,4,7};
    int[] input2 = getInputs(file);
    // for (int i=0; i<input1.length; i++) System.out.print(getWord(buffer,i*4) + " ");
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertArrayEquals(input1, input2);
    Assert.assertEquals(1, get(v0));
  }
  @Test
  public void rotate_270_3() throws FileNotFoundException {
    String file = "outputs/out.txt";
    Label inpt = asciiData(true, file);
    // row, col, matrix
    Label buffer = wordData(4, 4, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("rotate_clkws_270", buffer, inpt);
    int[] input1 = {4,4, 4,8,12,16,3,7,11,15,2,6,10,14,1,5,9,13};
    int[] input2 = getInputs(file);
    // for (int i=0; i<input1.length; i++) System.out.print(getWord(buffer,i*4) + " ");
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertArrayEquals(input1, input2);
    Assert.assertEquals(1, get(v0));
  }
  @Test
  public void mirror_1() throws FileNotFoundException {
    String file = "outputs/out.txt";
    Label inpt = asciiData(true, file);
    // row, col, matrix
    Label buffer = wordData(2, 3, 12, 3, 24,45, 6, 17, 0, 909, 0, 750, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("mirror", buffer, inpt);
    int[] input1 = {2,3, 24, 3, 12, 17, 6, 45};
    int[] input2 = getInputs(file);
    // for (int i=0; i<input1.length; i++) System.out.print(getWord(buffer,i*4) + " ");
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertArrayEquals(input1, input2);
    Assert.assertEquals(1, get(v0));
  }
  @Test
  public void mirror_2() throws FileNotFoundException {
    String file = "outputs/out.txt";
    Label inpt = asciiData(true, file);
    // row, col, matrix
    Label buffer = wordData(2, 2, 12, 3, 24,45, 6, 17, 0, 909, 0, 750, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("mirror", buffer, inpt);
    int[] input1 = {2,2, 3, 12, 45, 24,};
    int[] input2 = getInputs(file);
    // for (int i=0; i<input1.length; i++) System.out.print(getWord(buffer,i*4) + " ");
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertArrayEquals(input1, input2);
    Assert.assertEquals(1, get(v0));
  }
  @Test
  public void mirror_3() throws FileNotFoundException {
    String file = "outputs/out.txt";
    Label inpt = asciiData(true, file);
    // row, col, matrix
    Label buffer = wordData(9, 9, 1, 0, 0,0, 0, 0, 0, 0, 0, 1, 0, 0,0, 0, 0, 0, 0, 0, 1, 0, 0,0, 0, 0, 0, 0, 0, 1, 0, 0,0, 0, 0, 0, 0, 0,
    1, 0, 0,0, 0, 0, 0, 0, 0, 1, 0, 0,0, 0, 0, 0, 0, 0, 1, 0, 0,0, 0, 0, 0, 0, 0, 1, 0, 0,0, 0, 0, 0, 0, 0, 1, 0, 0,0, 0, 0, 0, 0, 0, 
    1, 0, 0,0, 0, 0, 0, 0, 0, 1, 0, 0,0, 0, 0, 0, 0, 0, 1, 0, 0,0, 0, 0, 0, 0, 0, 1, 0, 0,0, 0, 0, 0, 0, 0, 1, 0, 0,0, 0, 0, 0, 0, 0, 1);
    run("mirror", buffer, inpt);
    int[] input1 = {0, 0,0, 0, 0, 0, 0, 0, 1};
    int[] input2 = getInputs(file);
    for (int i=0; i<2; i++) Assert.assertEquals(9, getWord(buffer,i*4));
    // for (int j=0; j<9; j++) {
    //   for (int i=0; i<9; i++) System.out.print(input1[i%9] + " ");
    //   System.out.print(" |  ");
    //   for (int i=0; i<9; i++) System.out.print(getWord(buffer,8+4*(i+j*9)) + " ");
    //   System.out.println();
    // }
    for (int i=2; i<9*9; i++) Assert.assertEquals(input1[i%9], getWord(buffer,(i+2)*4));
    Assert.assertEquals(1, get(v0));
  }
  @Test
  public void duplicate_1f() throws FileNotFoundException {
    String file = "inputs/dup1f.txt";
    Label inpt = asciiData(true, file);
    int[] input1 = getInputs(file);
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertEquals(1, get(v0));
    Label new_buffer = wordData(getWords(buffer.address(), 102));
    run("duplicate", new_buffer);
    Assert.assertEquals(-1, get(v0));
  }  
  @Test
  public void duplicate_1p() throws FileNotFoundException {
    String file = "inputs/dup1p.txt";
    Label inpt = asciiData(true, file);
    int[] input1 = getInputs(file);
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Label new_buffer = wordData(getWords(buffer.address(), 102));
    Assert.assertEquals(1, get(v0));
    run("duplicate", new_buffer);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(3, get(v1));
  }
  @Test
  public void duplicate_2f() throws FileNotFoundException {
    String file = "inputs/dup2f.txt";
    Label inpt = asciiData(true, file);
    int[] input1 = getInputs(file);
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertEquals(1, get(v0));
    Label new_buffer = wordData(getWords(buffer.address(), 102));
    run("duplicate", new_buffer);
    Assert.assertEquals(-1, get(v0));
  }  
  @Test
  public void duplicate_2p() throws FileNotFoundException {
    String file = "inputs/dup2p.txt";
    Label inpt = asciiData(true, file);
    int[] input1 = getInputs(file);
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Label new_buffer = wordData(getWords(buffer.address(), 102));
    Assert.assertEquals(1, get(v0));
    run("duplicate", new_buffer);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(4, get(v1));
  }
  @Test
  public void duplicate_3f() throws FileNotFoundException {
    String file = "inputs/dup3f.txt";
    Label inpt = asciiData(true, file);
    int[] input1 = getInputs(file);
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Assert.assertEquals(1, get(v0));
    Label new_buffer = wordData(getWords(buffer.address(), 102));
    run("duplicate", new_buffer);
    Assert.assertEquals(-1, get(v0));
  }  
  @Test
  public void duplicate_3p() throws FileNotFoundException {
    String file = "inputs/dup3p.txt";
    Label inpt = asciiData(true, file);
    int[] input1 = getInputs(file);
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Label new_buffer = wordData(getWords(buffer.address(), 102));
    Assert.assertEquals(1, get(v0));
    run("duplicate", new_buffer);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(5, get(v1));
  }
  @Test
  public void duplicate_4p() throws FileNotFoundException {
    String file = "inputs/dup4p.txt";
    Label inpt = asciiData(true, file);
    int[] input1 = getInputs(file);
    // row, col, matrix
    Label buffer = wordData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    run("initialize", inpt, buffer);
    for (int i=0; i<input1.length; i++) Assert.assertEquals(input1[i], getWord(buffer,i*4));
    Label new_buffer = wordData(getWords(buffer.address(), 102));
    Assert.assertEquals(1, get(v0));
    run("duplicate", new_buffer);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(5, get(v1));
  }
}
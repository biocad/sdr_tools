package ru.biocad.ig.common.algorithms


import java.io.File
import com.typesafe.scalalogging.slf4j.LazyLogging

import ru.biocad.ig.common.io.pdb.{PDBStructure, PDBAtomInfo, PDBAminoacidCollection}

import ru.biocad.ig.common.structures.geometry._
import ru.biocad.ig.alascan.moves._

import ru.biocad.ig.common.structures.aminoacid.SimplifiedChain

import ru.biocad.ig.common.io.pdb.PDBWriter


/** Selects method of data processing from command line parameters.
  * Calls actual MC algorithm, which defined at [[ru.biocad.ig.common.algorithms.MonteCarlo]] object, with different parameters
  */
object MonteCarloRunner extends LazyLogging {
  val lattice = new Lattice()

  /** helper method, loads structure from Protein Data Bank file and then returns simplified representation to it.
    * NB! - Currently it supposes that PDB file contains valid structure, where all atoms are well-positioned
    * returns data for one particular chain, because current MC implementation supports 1-chained proteins.
    *
    * @param filename - name of valid pdb filename to process (all checks are neglected, because I'm a simple codemonkey with no hope, faith, fear, and future)
    * @param chain chain letter from corresponding ATOM portion from PDB data (chain names can also be found in structure description somewhere at the beginning)
    * @return pair of 2 objects - MC-ready simplified structure and sequence of pdb 'ATOM' lines, corresponding to chain params, grouped for each chain's aminoacid
    */
  def loadStructure(filename : String, chain : Char = 'L')  : (SimplifiedChain, Seq[Seq[PDBAtomInfo]]) = {
    println("loading structure from sample pdb...")
    val structure : PDBStructure = new PDBStructure()
    structure.readFile(getClass.getResource(filename).getFile())
    println("local file read - done")
    val aaByChain = PDBAminoacidCollection(structure)
    val aas = aaByChain.aminoacidsByChain.toSeq
    val filteredMap = SimplifiedChain(aas, lattice.latticeConstants.meshSize)//TODO : init lattice somewhere near this
    (filteredMap, aas)
  }

  /** improves current PDB structure (one chain gets improved, multichains are not supported now)
    *
    * @param inputFile PDB file with protein to improve
    * @param mcTimeUnits MC parameter, actual number of timeUnits
    */
  def refine(inputFile : File, mcTimeUnits : Int, outputFile : File) = {
    println("testing backbone reconstruction...")
    val (simplifiedChain, fullAtomChain) = loadStructure("/2OSL.pdb")
    logger.info("Energy before structure refinement: " + lattice.getEnergy(simplifiedChain).toString)

    //println(Lattice.getEnergy(simplifiedChain))
    val ch1 = MonteCarlo(lattice).run(simplifiedChain,
        getMovesForSequence(simplifiedChain.size),
        x => lattice.getEnergy(x), mcTimeUnits)
    logger.info("Energy after structure refinement: "+ lattice.getEnergy(ch1))
    val result = lattice.toFullAtomRepresentation(ch1, fullAtomChain)
    //val sidechainInfo = JsonParser(Source.fromURL(getClass.getResource("/sidechains.json")).getLines().mkString("")).convertTo[AminoacidLibrary[SidechainInfo]]
    val w = new PDBWriter(outputFile)
    w.writeAtomInfo(result)
    w.close()
  }

  def getMovesForSequence(n : Int) = {
     Seq(
      (new BondMove(lattice.backboneVectors, 2), n - 2),
      (new BondMove(lattice.backboneVectors, 4), n - 4),
      (new BondMove(lattice.backboneVectors, 6), n - 6),
      (new BondMove(lattice.backboneVectors, 8), n - 8),
      (new BondMove(lattice.backboneVectors, 10), n - 10),
      (new DisplacementMove(lattice.backboneVectors), 2),
      (new RotamerMove(lattice.sidechainsInfo), n)
    ).filter(_._2 > 0)
  }

  /** computes folded structure, starts from given aminoacid sequence
    *
    * @param sequence
    * @param mcTimeUnits
    * @param outputFile
    */
  def fold(sequence : String, mcTimeUnits : Int, outputFile : File) = {
    println("testing backbone reconstruction...")
    val simplifiedChain = SimplifiedChain.fromSequence(sequence, lattice)
    logger.info("Energy before structure refinement: " + lattice.getEnergy(simplifiedChain).toString)

    //println(Lattice.getEnergy(simplifiedChain))
    val ch1 = MonteCarlo(lattice).run(simplifiedChain, getMovesForSequence(simplifiedChain.size),
        x => lattice.getEnergy(x), mcTimeUnits)
    logger.info("Energy after structure refinement: "+ lattice.getEnergy(ch1))
    val result = lattice.toFullAtomRepresentation(ch1)
    val w = new PDBWriter(outputFile)
    w.writeAtomInfo(result)
    w.close()
    //TODO: construct full-atom chain with no pdb atom details
  }

  //TODO: change to alascan
  /** Should perform alanine scanning - currently NOT tested, currently copies refine method
    * Idea : in cycle change 1 aminoacid in sequence and perform 1 mc run. quite easy.
    * The main problem is to made genuine, informative report file.
    */
  def scan(inputFile : File, mcTimeUnits : Int, outputFile : File) = {
    println("testing backbone reconstruction...")
    val (simplifiedChain, fullAtomChain) = loadStructure("/2OSL.pdb")
    logger.info("Energy before structure refinement: " + lattice.getEnergy(simplifiedChain).toString)

    //println(Lattice.getEnergy(simplifiedChain))
    val ch1 = MonteCarlo(lattice).run(simplifiedChain, getMovesForSequence(simplifiedChain.size),
        x => lattice.getEnergy(x), mcTimeUnits)
    logger.info("Energy after structure refinement: "+ lattice.getEnergy(ch1))
    val result = lattice.toFullAtomRepresentation(ch1, fullAtomChain)
    //val sidechainInfo = JsonParser(Source.fromURL(getClass.getResource("/sidechains.json")).getLines().mkString("")).convertTo[AminoacidLibrary[SidechainInfo]]
    val w = new PDBWriter(outputFile)
    w.writeAtomInfo(result)
    w.close()
  }

}

package ru.biocad.ig.common.io.pdb

import java.io.{FileOutputStream, File}

/** Saves PDB 'ATOM' records to PDB file.
  *
  */
class PDBWriter(outputFile : File) {
  private val stream = new FileOutputStream(outputFile)

  def this(filename : String) = {
    this(new File(filename))
  }

  def writeAtomInfo(records: Iterable[PDBAtomInfo]) : Unit = {
    stream.synchronized {
      records.foreach(writeToStream)
    }
  }

  def close() : Unit = stream.close()

  private def writeToStream(record : PDBAtomInfo) : Unit = {
    stream.write((record.serialize() + "\n").getBytes)
  }
}

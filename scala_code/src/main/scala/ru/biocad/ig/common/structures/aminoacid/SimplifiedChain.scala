package ru.biocad.ig.common.structures.aminoacid

import scala.collection._

import ru.biocad.ig.common.io.pdb.PDBAtomInfo
import ru.biocad.ig.common.structures.geometry.GeometryVector
import ru.biocad.ig.common.structures.geometry.Lattice

/** hides sequence of simplified aminoacids and constructs them from various sources
  */
case class SimplifiedChain(val structure : Array[SimplifiedAminoacid]) extends Traversable[SimplifiedAminoacid] {
  override val size = structure.size

  def apply(index : Int) : SimplifiedAminoacid = structure(index)

  def foreach[U](f: SimplifiedAminoacid => U) = structure.foreach(f)

  def replaceAminoacid(positionFunc : (Int) => Boolean,
                       replaceFunction : (SimplifiedAminoacid) => SimplifiedAminoacid
                      ) : SimplifiedChain = {
    new SimplifiedChain(structure.zipWithIndex.map({
      case (el, i) => if (positionFunc(i)) replaceFunction(el) else el
    }))
  }

  def replaceRotamer(newRotamer : GeometryVector, position : Int) : SimplifiedChain = {
    replaceAminoacid({_ == position}, {case aa => new SimplifiedAminoacid(aa.name, aa.ca, newRotamer) })
  }

  def moveFragment(moveVector : GeometryVector, position : Int, numberOfBonds : Int) : SimplifiedChain = {
    replaceAminoacid({i => i >= position && i < position + numberOfBonds - 1},
      { case aa => aa.move(moveVector) })
  }


}

object SimplifiedChain {
  def apply(originalSequence : Seq[Seq[PDBAtomInfo]]) = {
    new SimplifiedChain(originalSequence.map(SimplifiedAminoacid(_)).toArray)
  }
}

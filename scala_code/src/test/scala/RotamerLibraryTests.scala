import org.scalatest.{Matchers, FlatSpec}

import spray.json._
import scala.io.Source

import ru.biocad.ig.alascan.constants.json.SidechainLibraryJsonProtocol
import ru.biocad.ig.alascan.constants.json.SidechainLibraryJsonProtocol._

import ru.biocad.ig.alascan.constants.{AminoacidLibrary, SidechainInfo}
import ru.biocad.ig.common.structures.aminoacid.SimplifiedAminoAcid

class RotamerLibraryTests extends FlatSpec with Matchers {
  it should "restore coordinates with given meshSize" in {
    val rotamerInfo = JsonParser(Source.fromURL(getClass.getResource("/sidechains.json")).getLines().mkString("")).convertTo[AminoacidLibrary[SidechainInfo]]
    (rotamerInfo.data("LEU")(20)(22)(-32)) should equal (
      rotamerInfo.restoreAminoAcidInfo("LEU",
        20*rotamerInfo.meshSize, 22*rotamerInfo.meshSize, -32*rotamerInfo.meshSize))
  }
  it should "correctly call setRotamerFromLibrary for empty SidechainInfo" in {
    val s = SidechainInfo(Seq(), Seq(), 0)
    val aa = new SimplifiedAminoAcid(Seq())
    noException should be thrownBy s.setRotamerFromLibrary(aa)
    val result = s.setRotamerFromLibrary(aa)
    result should equal(aa)
    (result.rotamer) should equal (aa.rotamer)
  }
  it should "correctly call changeRotamerToRandom for empty SidechainInfo" in {
    val s = SidechainInfo(Seq(), Seq(), 0)
    val aa = new SimplifiedAminoAcid(Seq())
    noException should be thrownBy s.changeRotamerToRandom(aa)
    val result = s.changeRotamerToRandom(aa)
    result should equal(aa)
    (result.rotamer) should equal (aa.rotamer)
  }
}

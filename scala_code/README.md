TODO \ FIX
------------


2. компоненты функции энергии, + поставлены у более-менее готовых

    - [x] E_CA-trace
    - [x] E_one
    - [x] E_SG-local
    - [x] E_pair
    - [x] E_tem
    - [x] E_rot
    - [x] E_H-bond

5. число контактов. сейчас считается с использованием aminoacid pair-specific расстояния.
по идее надо использовать хитрое kd-дерево с свистелками - иммутабельное, с группировками точек по аминокислотам (т.к. в отдельных статьях упоминается, что контакт между аминокислотами зависит от расстояния между ближайшими тяжелыми атомами, а не только между центрами масс боковых групп), с перестроением в новое kd-дерево при модификации положений точек.

5. тест для фолдинга: использовать для теста те же pdb-структуры, которые используются в статье. Для генерации библиотеки ротамеров, которая используется для восстановления полного представления цепочек, использовать те же данные, что и в статье. Сравнить результат.

Оставшиеся задачи:

- [x] водородные связи (не совсем понимаю, правильно сделала или нет)
- [x] валидация структуры после шага монте-карло
- [x] восстановление боковых цепей
- [x] rotamerMove
- [x] сделать SimplifiedAminoacid иммутабельным
- [x] края
- [x] библиотеку ротамеров заполнить нормальными данными
- [x] desmond
- [x] путаница с названиями, классами - разобраться
- [x] тесты для штук с ротамерами: надо еще проверить боковые цепи и корректность вывода (порядок вывода PDBAtomInfo)
- [x] упрощение структуры: попробовать восстанавливать атомы между двумя CA, а не так, как сейчас. в отдельной ветке. сравнить с текущим выводом, если лучше, то использовать его (у меня получилась картинка не очень)
- [x] пайплайн desmond/maestro - составить порядок запуска - отдельная задача.
- [x] пайплайн desmond/maestro - отдельный класс с настройками. в ig-toolkit смотреть не буду, т.к. вроде бы сейчас актуален ylab? и если есть какие-то готовые штуки для последовательного вызова команд, то смотреть надо там.
- [ ] понять, надо ли мне собственно вызывать десмонд, или достаточно минимизации? (опционально)
- [ ] настроить, чтобы десмонд спамил в отдельную папку (опционально)
- [ ] перенести постпроцессинг из ресурсов в настройки (опционально)
- [ ] конвертировать результат работы desmond в pdb обратно (опционально)
        (на desmond лицензия закончилась, с ним ничего переделывать не буду)
- [x] qhull
- [x] слайды
- [ ] аласкан (найти статью или кусок книги, где было о том, как его делать - понятно, что монте-карло надо в окрестности запускать, в какой? ну и мб решить все-таки нужно кд-дерево или нет)
- [x] сам монте-карло: посмотреть в других статьях тех же авторов, может, все-таки они используют какую-то хитрую схему (добавила температуру и еще что-то)


Литература
----------

[feig2000] Michael Feig, Piotr Rotkiewicz, Andrzej Kolinski, Jeffrey Skolnick, and Charles L. Brooks III. Accurate Reconstruction of All-Atom Protein Representations From Side-Chain-Based Low-Resolution Models. PROTEINS: Structure, Function, and Genetics, 41:86-97, 2000.
URL: http://www.ncbi.nlm.nih.gov/pubmed/10944396

[folding100] Andrzej Kolinski, Jeffrey Skolnick. Discretized model of proteins. I. Monte Carlo study of cooperativity in homopolypeptides. Journal of computational chemistry, 97(12), 1992.
URL: http://cssb.biology.gatech.edu/skolnick/publications/pdffiles/100.pdf

[folding107] Andrzej Kolinski, Adam Godzik, Jeffrey Skolnick. A general method for the prediction of the three dimensional structure and folding pathway of globular proteins: Application to designed helical proteins. Journal of computational chemistry, 98(9), 1993.
URL: http://cssb.biology.gatech.edu/skolnick/publications/pdffiles/107.pdf

[folding116] Andrzej Kolinski, Jeffrey Skolnick. Monte Carlo Simulations of Protein Folding. I. Lattice Model and Interaction Scheme. PROTEINS: Structure, Function, and Genetics, 18:338-352, 1994.
URL: http://cssb.biology.gatech.edu/skolnick/publications/pdffiles/116.pdf

[folding142] Michal Vieth, Andrzej Kolinski, Charles L. Brooks III, Jeffrey Skolnick.
Prediction of quaternary structure of coiled coils. Application to mutants of GCN4 Leucine Zipper.
J.Mol.Biol., 251:448-467, 1995.
URL: http://cssb.biology.gatech.edu/skolnick/publications/pdffiles/142.pdf


[folding163] Andrzej Kolinski, Jeffrey Skolnick. Assembly of Protein Structure From Sparse Experimental Data: An Efficient Monte Carlo Model.
PROTEINS: Structure, Function, and Genetics, 32:475-494, 1998.
URL: http://cssb.biology.gatech.edu/skolnick/publications/pdffiles/163.pdf

[levitt1976] M.Levitt. A Simplified Representation of Protein Conformations for Rapid Simulation of Protein Folding. J.Mol.Biol., 104:59-107, 1976.

[levitt1977] M. Levitt, J.Greer. Automatic  identification of secondary structure in globular proteins. Journal of Molecular Biology, 114:181-293, 1977.

[MCDP_dataset] Michal Vieth, Andrzej Kolinski, Jeffrey Skolnick. Coiled-coils folding parameters. 1994b.
URL: ftp://ftp.scripps.edu/pub/brooks/MCDP/

[milik1997] Mariusz Milik, Andrzej Kolinski, Jeffrey Skolnick. Algorithm for rapid reconstruction of protein backbone from alpha carbon coordinates. Journal of computational chemistry, 18: 80-85, 1997.
URL: http://www.researchgate.net/publication/220417855_Algorithm_for_rapid_reconstruction_of_protein_backbone_from_alpha_carbon_coordinates

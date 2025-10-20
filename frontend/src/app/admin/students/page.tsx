"use client";
import { useState } from "react";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
} from "@/components/ui/select";
import MarksTable from "@/components/MarksTable";

// --- Fake data pour le test ---
const filieres = ["Informatique", "Génie Logiciel", "Réseaux"];
const matieresParFiliere: Record<string, string[]> = {
  Informatique: ["Algorithmique", "Systèmes", "Mathématiques"],
  "Génie Logiciel": ["Programmation", "Base de Données", "UML"],
  Réseaux: ["Sécurité", "Administration", "Télécom"],
};

const fakeGrades = [
  {
    id: 1,
    firstName: "Ben Salah",
    lastName: "Amine",
    ds: 15.2,
    tp: 10,
  },
  {
    id: 2,
    firstName: "Trabelsi",
    lastName: "Sarra",
    ds: 9.8,
    tp: 10,
  },
  { id: 3, firstName: "Mansour", lastName: "Ali", ds: 12.5, tp: 10 },
  { id: 4, firstName: "Gharbi", lastName: "Nour", ds: 7.3, tp: 10 },
  { id: 5, firstName: "Khaled", lastName: "Rania", ds: 10.0, tp: 10 },
];

const columns = ["#", "firsName", "lastName", "DS", "TP", "EXAM"];

export default function SubjectGradesPage() {
  const [selectedFiliere, setSelectedFiliere] = useState<string>("");
  const [selectedMatiere, setSelectedMatiere] = useState<string>("");

  const matieres = selectedFiliere ? matieresParFiliere[selectedFiliere] : [];

  return (
    <div className="p-6 space-y-6">
      <h2 className="text-2xl font-semibold text-gray-800">
        Notes par matière et filière
      </h2>

      {/* --- Filtres --- */}
      <Card>
        <CardHeader>
          <CardTitle>Filtrer les résultats</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="flex flex-col md:flex-row gap-4">
            {/* Select Filière */}
            <Select onValueChange={setSelectedFiliere}>
              <SelectTrigger className="w-[220px]">
                <SelectValue placeholder="Choisir une filière" />
              </SelectTrigger>
              <SelectContent>
                {filieres.map((f) => (
                  <SelectItem key={f} value={f}>
                    {f}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>

            {/* Select Matière */}
            <Select
              onValueChange={setSelectedMatiere}
              disabled={!selectedFiliere}
            >
              <SelectTrigger className="w-[220px]">
                <SelectValue placeholder="Choisir une matière" />
              </SelectTrigger>
              <SelectContent>
                {matieres.map((m) => (
                  <SelectItem key={m} value={m}>
                    {m}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
        </CardContent>
      </Card>

      {/* --- Tableau des notes --- */}
      {selectedFiliere && selectedMatiere && (
        <MarksTable
          columns={columns}
          title={`Notes des étudiants — ${selectedMatiere} (${selectedFiliere})`}
          rows={fakeGrades}
        />
      )}
    </div>
  );
}

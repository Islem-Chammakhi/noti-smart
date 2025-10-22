"use client";
import { useEffect, useState } from "react";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
} from "@/components/ui/select";
import MarksTable from "@/components/MarksTable";
import myApi from "@/lib/api";
import { Loader2 } from "lucide-react";

// --- Fake data pour le test ---
const filieres = ["ING2_INFO"];
const matieresParFiliere: Record<string, string[]> = {
  ING2_INFO: ["397"],
};

const columns = ["cin", "nom & prénom", "ds", "tp/orale", "examen"];

export default function SubjectGradesPage() {
  const [selectedFiliere, setSelectedFiliere] = useState<string>("");
  const [selectedMatiere, setSelectedMatiere] = useState<string>("");
  const [data, setData] = useState<StudentMarks[]>([]);
  const [loading, setLoading] = useState(false);

  const fetchMarks = async (filiereId: string, subjectId: string) => {
    try {
      setLoading(true);
      const response = await myApi.getStudentMarksByFiliereAndSubject(
        filiereId,
        subjectId
      );
      if (response.status === 200) {
        setData(response.data);
      }
    } catch (err) {
      console.log(err);
    } finally {
      setLoading(false);
    }
  };
  useEffect(() => {
    if (selectedFiliere && selectedMatiere)
      fetchMarks(selectedFiliere, selectedMatiere);
  }, [selectedFiliere, selectedMatiere]);

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
      <div>
        {loading ? (
          <div className="flex justify-center py-8">
            <Loader2 className="animate-spin w-6 h-6 text-blue-600" />
          </div>
        ) : data.length > 0 ? (
          <MarksTable
            columns={columns}
            title={`Notes des étudiants — ${selectedMatiere} (${selectedFiliere})`}
            rows={data}
          />
        ) : (
          <p className="text-center text-gray-500 py-6">
            Aucune donnée à afficher
          </p>
        )}
      </div>
    </div>
  );
}

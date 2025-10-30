"use client";
import { useState } from "react";
import EvaluationPieCharts from "../components/EvaluationPieCharts";
import SimpleRadarChart from "../components/RadarChart";

export default function FiliereFilter() {
  const [filiere, setFiliere] = useState("");

  return (
    <div className="space-y-6 p-6">
      <h2 className="text-2xl font-semibold text-gray-800">
        Statistiques par étudiant
      </h2>
      {/* <Card className="p-4">
        <CardHeader>
          <CardTitle>Sélectionner une filière</CardTitle>
        </CardHeader>
        <Select onValueChange={setFiliere}>
          <SelectTrigger className="w-[200px]">
            <SelectValue placeholder="Choisir une filière" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="informatique">Informatique</SelectItem>
            <SelectItem value="Télécommunications">
              Télécommunications
            </SelectItem>
            <SelectItem value="Réseaux et télécoms">
              Réseaux et télécoms
            </SelectItem>
          </SelectContent>
        </Select>
      </Card> */}
      {/* <AverageSubjectsPerFiliere /> */}
      <EvaluationPieCharts />
      <SimpleRadarChart />
    </div>
  );
}

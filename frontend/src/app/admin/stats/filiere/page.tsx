"use client";

import { Card, CardHeader, CardTitle } from "@/components/ui/card";

import { useEffect, useState } from "react";
import SubjectPerformanceChart from "../components/SubjectPerformanceChart";
import AverageSubjectsPerFiliere from "../components/AverageSubjectsPerFiliere";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import myApi from "@/lib/api";
import Loader from "@/components/Loader";

export default function FiliereFilter() {
  const [filiere, setFiliere] = useState("");
  const [loading, setLoading] = useState(false);
  const [subjectGeneralAverage, setSubjectGeneralAverage] = useState<
    SubjectGeneralAverage[]
  >([]);
  const [subjectsAverageStats, setSubjectsAverageStats] = useState<
    SubjectAverageStats[]
  >([]);

  const fetchData = async (filiereId: string) => {
    try {
      setLoading(true);
      const [res1, res2] = await Promise.all([
        await myApi.getSubjectGeneralAverage(filiereId),
        await myApi.getSubjectAverageStats(filiereId),
      ]);
      if (res1.status === 200) {
        setSubjectGeneralAverage(res1.data);
      }
      if (res2.status === 200) {
        setSubjectsAverageStats(res2.data);
      }
    } catch (err) {
      console.log(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    filiere && fetchData(filiere);
  }, [filiere]);
  return (
    <>
      {loading && <Loader />}
      <div className="space-y-6 p-6">
        <h2 className="text-2xl font-semibold text-gray-800">
          Statistiques par filiére
        </h2>
        <Card className="p-4">
          <CardHeader>
            <CardTitle>Sélectionner une filière</CardTitle>
          </CardHeader>
          <Select onValueChange={setFiliere}>
            <SelectTrigger className="w-[200px]">
              <SelectValue placeholder="Choisir une filière" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="ING2_INFO">ING2_INFO</SelectItem>
              {/* <SelectItem value="Télécommunications">
                Télécommunications
              </SelectItem>
              <SelectItem value="Réseaux et télécoms">
                Réseaux et télécoms
              </SelectItem> */}
            </SelectContent>
          </Select>
        </Card>
        {subjectGeneralAverage.length === 0 &&
        subjectsAverageStats.length === 0 ? (
          <p className="text-center text-gray-500 py-6">
            Aucune donnée à afficher
          </p>
        ) : (
          <>
            <AverageSubjectsPerFiliere data={subjectGeneralAverage} />
            <SubjectPerformanceChart data={subjectsAverageStats} />
          </>
        )}
      </div>
    </>
  );
}

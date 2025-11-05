"use client";
import { useEffect, useState } from "react";
import EvaluationPieCharts from "../components/EvaluationPieCharts";
import SimpleRadarChart from "../components/RadarChart";
import { useAuth } from "@/hooks/useAuth";
import myApi from "@/lib/api";

export default function FiliereFilter() {
  const { user } = useAuth();

  const [loading, setLoading] = useState(false);
  const [subjectGeneralAverage, setSubjectGeneralAverage] = useState<
    SubjectGeneralAverage[]
  >([]);

  const fetchData = async () => {
    if (!user) {
      setSubjectGeneralAverage([]);
    } else {
      try {
        setLoading(true);

        const res1 = await myApi.getSubjectsAverageByStudent(user?.cin);
        if (res1.status === 200) {
          setSubjectGeneralAverage(res1.data);
        }
      } catch (err) {
        console.log(err);
      } finally {
        setLoading(false);
      }
    }
  };
  useEffect(() => {
    user && fetchData();
  }, []);
  return (
    <div className="space-y-6 p-6">
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
      <SimpleRadarChart data={subjectGeneralAverage} />
    </div>
  );
}

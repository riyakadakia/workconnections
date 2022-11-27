import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { surveyClient } from "../client/surveyClient";
import { ProgramCard } from "../components/ProgramCard";
import { Program } from "../types";
import { None } from "../utils/None";

export function Programs() {
  const [eligiblePrograms, setEligiblePrograms] = useState<Program[]>([]);

  // This state will be used to tell if a new session start is underway
  const [isLoading, setIsLoading] = useState(false);

  // We'll need to navigate to the survey page
  const navigate = useNavigate();

  const sessionId = new URLSearchParams(window.location.search).get("sessionId");
  if (None(sessionId) || sessionId === "") {
    navigate("/");
  }

  useEffect(() => {
    const getEligiblePrograms = async () => {
      if (sessionId != null) {
        const eligiblePrograms = await surveyClient.getEligiblePrograms(sessionId);
        setEligiblePrograms(eligiblePrograms);
      } else {
        navigate("/");
      }
    };
    getEligiblePrograms();
  }, [eligiblePrograms.length]);

  return (
    <>
      {eligiblePrograms.length > 0 && (
        <div>
          {eligiblePrograms
            .filter((p) => p != null)
            .map((p) => (
              <ProgramCard key={p.id} program={p} />
            ))}
        </div>
      )}
    </>
  );
}

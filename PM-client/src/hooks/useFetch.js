import { useEffect, useState } from 'react'

const useFetch = (url) => {

    const [data, setData] = useState(null)
    const [isLoading, setIsLoading] = useState(true)
    const [error, setError] = useState(null)

    const accessToken = localStorage.getItem('access_token')

    useEffect(() => {

        const abortCont = new AbortController()

        fetch(url, { 
            headers: {
                Authorization: `Bearer ${accessToken}`
            },
            signal: abortCont.signal
        })
            .then(res => {
                if (!res.ok) {
                    throw Error('Could not fetch the data for that resource')
                }
                return res.json()
            })
            .then(data => {
                setData(data)
                setIsLoading(false)
                setError(null)
            })
            .catch(err => {
                if (err.name === 'AbortError') {
                    console.log('fetch aborted')
                } else {
                    setIsLoading(false)
                    setError(err.message)
                }
            })

        return () => abortCont.abort()
    }, [url])


    return { data, isLoading, error }
}

export default useFetch